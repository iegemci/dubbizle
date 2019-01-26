package com.enesgemci.dubbizle.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.App
import com.enesgemci.dubbizle.core.dagger.component.DaggerDialogFragmentComponent
import com.enesgemci.dubbizle.core.dagger.component.DialogFragmentComponent
import com.enesgemci.dubbizle.core.dagger.module.DialogFragmentModule
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder.NO_NAV_ICON
import com.enesgemci.dubbizle.core.util.AlertUtil
import com.enesgemci.dubbizle.core.util.UIUtil
import com.enesgemci.dubbizle.view.MProgressDialog
import timber.log.Timber
import kotlin.properties.Delegates

abstract class BaseDialogFragment<V : BaseView, P : BasePresenter<V>> :
    BaseMvpDialogFragment<V, P>(),
    BaseView {

    private var navigationBuilder: NavigationBuilder<*>? = null

    protected var toolbar: Toolbar? = null

    private var progressDialog: MProgressDialog by Delegates.notNull()

    override val resourceLayoutId: Int
        get() = 0

    val isFullScreen: Boolean
        get() = false

    val animationId: Int
        @StyleRes
        get() = R.style.DialogAnimation

    override val isConnected: Boolean
        get() = UIUtil.isConnectedToNet(requireContext())

    private var dialogFragmentComponent: DialogFragmentComponent? = null

    abstract fun injectFragment(component: DialogFragmentComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectFragment(getDialogFragmentComponent())
        super.onCreate(savedInstanceState)

        progressDialog = MProgressDialog(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        assignListener()
    }

    open fun assignListener() {
        //Rent to override in child classes
    }

    protected fun isAssignableFrom(clazz: Class<*>, `object`: Any?): Boolean {
        return `object` != null && clazz.isAssignableFrom(`object`.javaClass)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationBuilder = buildNavigation()

        return navigationBuilder?.getLayoutFactory()?.produceLayout(inflater, container)
    }

    private fun getDialogFragmentComponent(): DialogFragmentComponent {
        if (dialogFragmentComponent == null) {
            dialogFragmentComponent = DaggerDialogFragmentComponent.builder()
                .dialogFragmentModule(DialogFragmentModule(this))
                .appComponent(App.appComponent)
                .build()
        }
        return dialogFragmentComponent!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(navigationBuilder?.getToolbarId() ?: 0)
        prepareNavigation()
    }

    override fun onResume() {

        if (isFullScreen) {
            dialog?.window?.let {
                val params = it.attributes
                params.width = LayoutParams.MATCH_PARENT
                params.height = LayoutParams.MATCH_PARENT
                it.attributes = params as LayoutParams

                if (animationId != -1) {
                    it.setWindowAnimations(animationId)
                }
            }
        }

        super.onResume()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        if (isFullScreen) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Dialog_FullScreen)
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun showLoadingDialog() {
        progressDialog.show()
    }

    override fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

    fun dismissDialogFragment() {
        dismiss()
    }

    protected fun prepareToolbar(toolbar: Toolbar) {

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
                    toolbar.setNavigationOnClickListener { dismiss() }
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

    protected abstract fun buildNavigation(): NavigationBuilder<*>

    protected fun invalidateNavigation(newNavigation: NavigationBuilder<*>) {
        navigationBuilder = newNavigation
        prepareNavigation()
    }

    private fun prepareNavigation() {
        toolbar?.let {
            prepareToolbar(it)
        }
    }

    protected fun setToolbarTitle(text: String) {
        toolbar?.title = text
    }

    override fun showMessage(message: String) {
        view?.let { AlertUtil.showSuccess(it, message) }
    }
}
