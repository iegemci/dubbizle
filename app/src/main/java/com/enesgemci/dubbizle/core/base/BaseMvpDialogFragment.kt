package com.enesgemci.dubbizle.core.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import javax.inject.Inject

abstract class BaseMvpDialogFragment<V : BaseView, P : BasePresenter<V>> : AppCompatDialogFragment(),
    MvpDelegateCallback<V, P>, BaseView {

    /**
     * Method to get fragment's UI content layout resource id.
     *
     * @return The fragment's root view's resource id
     */
    protected abstract val resourceLayoutId: Int

    private var mvpDelegate: FragmentMvpDelegate<V, P>? = null
        get() {
            if (field == null) {
                field = FragmentMvpDelegateImpl<V, P>(
                    this,
                    this,
                    true,
                    false
                )
            }

            return field
        }

    @Inject
    lateinit var mPresenter: P

    final override fun createPresenter(): P = mPresenter

    override fun getPresenter(): P = mPresenter

    override fun setPresenter(presenter: P) {
        this.mPresenter = presenter
    }

    override fun getMvpView(): V = this as V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(resourceLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate?.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        mvpDelegate?.onDestroyView()
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate?.onCreate(savedInstanceState)
    }

    override fun onPause() {
        mvpDelegate?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate?.onResume()
    }

    override fun onDestroy() {
        mvpDelegate!!.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate?.onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate?.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mvpDelegate?.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate?.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate?.onSaveInstanceState(outState)
    }
}

