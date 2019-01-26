package com.enesgemci.dubbizle.core.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.Toolbar
import com.enesgemci.dubbizle.core.App
import com.enesgemci.dubbizle.core.dagger.component.DaggerFragmentComponent
import com.enesgemci.dubbizle.core.dagger.component.FragmentComponent
import com.enesgemci.dubbizle.core.dagger.module.FragmentModule
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder.NO_NAV_ICON
import com.enesgemci.dubbizle.core.util.AlertUtil
import com.enesgemci.dubbizle.core.util.UIUtil
import com.enesgemci.dubbizle.view.MProgressDialog
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

abstract class BaseFragment<V : BaseView, P : BasePresenter<V>> :
    MvpFragment<V, P>(),
    BaseView,
    CoroutineScope {

    private var progressDialog: MProgressDialog by Delegates.notNull()

    protected var mContainer: View? = null

    protected var toolbar: Toolbar? = null

    private var navigationBuilder: NavigationBuilder<*>? = null

    @Inject
    internal lateinit var injectedPresenterDontUseThisDirectly: P

    /**
     * This is the job for all coroutines started by this Fragment.
     *
     * Cancelling this job will cancel all coroutines started by this Fragment.
     */
    private val fragmentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + fragmentJob

    override val isConnected: Boolean
        get() = UIUtil.isConnectedToNet(requireContext())

    private val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule(this))
            .appComponent(App.appComponent)
            .build()
    }

    abstract fun onFragmentStarted()

    abstract fun injectFragment(component: FragmentComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectFragment(fragmentComponent)
        super.onCreate(savedInstanceState)

        progressDialog = MProgressDialog(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationBuilder = buildNavigation()

        mContainer = navigationBuilder?.getLayoutFactory()?.produceLayout(inflater, container)

        return mContainer
    }

    protected fun isAssignableFrom(clazz: Class<*>, obj: Any?): Boolean {
        return obj != null && clazz.isAssignableFrom(obj.javaClass)
    }

    open fun assignListener() {
        //Rent to override in child classes
    }

    protected fun <T> findListener(listenerType: Class<T>): T? {
        if (isAssignableFrom(listenerType, parentFragment)) {
            return parentFragment as? T?
        }

        if (isAssignableFrom(listenerType, activity)) {
            return activity as? T?
        }

        Timber.d(javaClass.simpleName + " can't find any listener for " + listenerType.simpleName)
        return null
    }

    fun getResourceLayoutId(): Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        assignListener()
    }

    override fun createPresenter(): P {
        return injectedPresenterDontUseThisDirectly
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()

        if (requireActivity().requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

        if (mContainer?.viewTreeObserver?.isAlive == true) {
            mContainer?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    mContainer?.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                    if (presenter.isViewAttached) {
                        onFragmentStarted()
                    }
                }
            })
        } else {
            if (presenter.isViewAttached) {
                onFragmentStarted()
            }
        }
    }

    override fun onDestroyView() {
        fragmentJob.cancel()

        dismissLoadingDialog()

        super.onDestroyView()
    }

    override fun showLoadingDialog() {
        progressDialog.show()
    }

    override fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

    protected abstract fun buildNavigation(): NavigationBuilder<*>

    private fun prepareToolbar(toolbar: Toolbar) {
        navigationBuilder?.let {
            if (it.getToolbarTitleRes() != 0) {
                toolbar.setTitle(it.getToolbarTitleRes())
            } else {
                toolbar.title = it.getToolbarTitle()
            }

            if (it.getToolbarSubtitleRes() != 0) {
                toolbar.setSubtitle(it.getToolbarSubtitleRes())
            } else {
                toolbar.subtitle = it.getToolbarSubtitle()
            }

            if (it.getToolbarLogoRes() != 0) {
                toolbar.setLogo(it.getToolbarLogoRes())
            } else {
                toolbar.logo = it.getToolbarLogo()
            }

            if (it.getToolbarNavigationIcon() == NO_NAV_ICON) {
                toolbar.navigationIcon = null
                toolbar.setNavigationOnClickListener(null)
            } else {
                val navIcon = it
                    .getNavigationDefaults()
                    .navigationIcons()
                    .fromType(it.getToolbarNavigationIcon())

                if (navIcon != null) {
                    toolbar.navigationIcon = navIcon.iconDrawable(toolbar.context)
                    toolbar.setNavigationOnClickListener(
                        it.getNavigationDefaults().navigationIconListener()
                    )
                }
            }


            val menu = toolbar.menu

            menu?.clear()

            if (!it.getMenuRes().isNullOrEmpty()) {
                val actions = it.getMenuActions().build()

                for (menuRes in it.getMenuRes()) {
                    toolbar.inflateMenu(menuRes)
                }

                toolbar.setOnMenuItemClickListener { item -> actions.onMenuItemClick(item) }
            }
        }
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilder<*>) {
        navigationBuilder = newNavigation
        prepareNavigation()
    }

    private fun prepareNavigation() {
        toolbar?.let { prepareToolbar(it) }
    }

    protected fun setToolbarTitle(text: String?) {
        if (toolbar == null) {
            initializeToolbar()
        }

        toolbar?.title = text ?: toolbar?.title
    }

    protected fun setToolbarLogo(drawable: Drawable?) {
        if (toolbar == null) {
            initializeToolbar()
        }

        toolbar?.logo = drawable
    }

    private fun initializeToolbar() {
        navigationBuilder?.let {
            toolbar = view?.findViewById(it.getToolbarId())
            prepareNavigation()
        }
    }

    fun getFragmentTag(): String {
        return this.javaClass.simpleName
    }

    protected fun hideSystemUI() {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    override fun showMessage(message: String) {
        view?.let { AlertUtil.showError(it, message) }
    }
}
