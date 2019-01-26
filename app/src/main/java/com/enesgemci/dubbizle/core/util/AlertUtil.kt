package com.enesgemci.dubbizle.core.util

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.androidadvance.topsnackbar.TSnackbar
import com.enesgemci.dubbizle.R

/**
 * Created by egemci on 1/9/18.
 */

object AlertUtil {

    private const val ICON_SIZE = 15
    private const val ICON_PADDING = 32
    private const val MAX_LINES = 3

    private const val ERROR = 99
    private const val SUCCESS = 100

    @JvmOverloads
    fun showError(
        anchorView: View, message: String,
        dismissListener: View.OnClickListener? = null
    ) {
        showSnack(anchorView, message, dismissListener, ERROR)
    }

    @JvmOverloads
    fun showSuccess(
        anchorView: View, message: String,
        dismissListener: View.OnClickListener? = null
    ) {
        showSnack(anchorView, message, dismissListener, SUCCESS)
    }

    private fun showSnack(anchorView: View, message: String, dismissListener: View.OnClickListener?, error: Int) {
        val snackbar = TSnackbar.make(anchorView, message, TSnackbar.LENGTH_LONG)

        val snackBarView = snackbar.view
        snackBarView.setOnClickListener { v ->
            snackbar.dismiss()

            dismissListener?.onClick(v)
        }

        snackbar.setCallback(object : TSnackbar.Callback() {
            override fun onDismissed(snackbar: TSnackbar?, event: Int) {
                super.onDismissed(snackbar, event)

                dismissListener?.onClick(snackBarView)
            }
        })

        when (error) {
            ERROR -> {
                snackbar.setIconLeft(R.drawable.ic_remove, ICON_SIZE.toFloat())
                snackBarView.setBackgroundResource(R.color.red)
            }
            SUCCESS -> {
                snackbar.setIconLeft(R.drawable.ic_tick, ICON_SIZE.toFloat())
                snackBarView.setBackgroundResource(R.color.green)
            }
        }

        val snackTextView = snackBarView.findViewById<TextView>(R.id.snackbar_text)

        UIUtil.applyColorFilterToDrawable(
            anchorView.context,
            snackTextView.compoundDrawables[0],
            R.color.white
        )

        snackTextView.setTextColor(ContextCompat.getColor(anchorView.context, R.color.white))
        snackTextView.compoundDrawablePadding = UIUtil.dpToPx(anchorView.context, ICON_PADDING).toInt()
        snackTextView.maxLines = MAX_LINES

        snackbar.show()
    }
}
