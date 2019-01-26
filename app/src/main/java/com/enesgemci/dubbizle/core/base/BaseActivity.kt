package com.enesgemci.dubbizle.core.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.App
import com.enesgemci.dubbizle.core.dagger.component.ActivityComponent
import com.enesgemci.dubbizle.core.dagger.component.DaggerActivityComponent
import com.enesgemci.dubbizle.core.dagger.module.ActivityModule
import com.enesgemci.dubbizle.core.util.AlertUtil
import com.enesgemci.dubbizle.core.util.UIUtil
import com.enesgemci.dubbizle.view.MProgressDialog
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> :
    MvpActivity<V, P>(), BaseView {

    @Inject
    lateinit var _presenter: P

    protected val contentResourceId: Int
        @LayoutRes
        get() = R.layout.activity_base

    protected val baseFrameLayoutId: Int
        get() = R.id.activity_base_frame

    override val isConnected: Boolean
        get() = UIUtil.isConnectedToNet(this)

    private var progressDialog: MProgressDialog by Delegates.notNull()

    abstract fun injectActivity(component: ActivityComponent)

    /**
     * Get initial fragment instance.
     *
     * @return Fragment.
     */
    protected abstract fun getContainedFragment(): Fragment

    protected fun getContainedFragmentTag(): String? {
        return null
    }

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent(App.appComponent)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity(activityComponent)

        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.anim_horizontal_fragment_in, R.anim.anim_horizontal_fragment_out)
        setContentView(contentResourceId)

        if (savedInstanceState == null) {

            val fragment = getContainedFragment()
            var tag = getContainedFragmentTag()

            if (tag == null && fragment is BaseFragment<*, *>) {
                tag = fragment.getFragmentTag()
            }

            supportFragmentManager
                .beginTransaction()
                .replace(baseFrameLayoutId, fragment, tag)
                .commit()
        }

        progressDialog = MProgressDialog(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)

        if (rootView.viewTreeObserver.isAlive) {
            rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

                override fun onGlobalLayout() {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    onActivityStarted()
                }
            })
        } else {
            onActivityStarted()
        }
    }

    final override fun createPresenter(): P = _presenter

    abstract fun onActivityStarted()

    override fun showLoadingDialog() {
        progressDialog.show()
    }

    override fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

    override fun finish() {
        this.overridePendingTransition(
            R.anim.anim_horizontal_fragment_in_from_pop,
            R.anim.anim_horizontal_fragment_out_from_pop
        )
        super.finish()
    }

    override fun showMessage(message: String) {
        AlertUtil.showSuccess(findViewById(baseFrameLayoutId), message)
    }
}
