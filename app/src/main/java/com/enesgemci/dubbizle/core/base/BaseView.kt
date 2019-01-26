package com.enesgemci.dubbizle.core.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView : MvpView {

    fun dismissLoadingDialog()

    fun showLoadingDialog()

    fun showMessage(message: String)

    val isConnected: Boolean
}
