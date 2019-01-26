package com.enesgemci.dubbizle.core.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseBottomSheetDialogFragment<V : BaseView, P : BasePresenter<V>> : BaseDialogFragment<V, P>() {

    private var onCancelListener: BaseBottomSheetDialogFragmentOnCancelListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    fun setOnCancelListener(listener: BaseBottomSheetDialogFragmentOnCancelListener) {
        onCancelListener = listener
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.onCanceled()
    }

    interface BaseBottomSheetDialogFragmentOnCancelListener {
        fun onCanceled()
    }
}
