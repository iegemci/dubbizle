package com.enesgemci.dubbizle.core.util

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * UI related utility methods.
 */
object UIUtil {

    /**
     * Android namespace.
     */
    @JvmField
    val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"

    /**
     * Checks if there is an active internet connection or not
     * @param context Context
     */
    @JvmStatic
    fun isConnectedToNet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var connected = false
        val networkInfoList = connectivityManager.allNetworks

        networkInfoList?.forEach { network ->
            val networkInfo = connectivityManager.getNetworkInfo(network)

            networkInfo?.let {
                connected = it.isConnected

                if (connected) {
                    return true
                }
            }
        }

        return connected
    }

    /**
     * To convert given dp value to px value.
     *
     * @param context Context
     * @param dp      value to be converted
     * @return converted px value
     */
    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
    }

    /**
     * To change given drawable's color.
     *
     * @param context  Context
     * @param drawable drawable to to be filtered
     * @param colorRes color to be applied to drawable
     */
    @JvmStatic
    fun applyColorFilterToDrawable(context: Context, drawable: Drawable, @ColorRes colorRes: Int) {
        drawable.mutate().setColorFilter(
            ContextCompat.getColor(context, colorRes),
            PorterDuff.Mode.SRC_IN
        )
    }

    /**
     * Returns color value for given hex string
     *
     * @param colorHex  6 digit hex string
     * @param defaultColor resolved color in case parsing fails
     */
    @JvmStatic
    fun getColorFromHex(colorHex: String, defaultColor: Int): Int {

        return try {
            Color.parseColor(colorHex)
        } catch (e: Exception) {
            defaultColor
        }
    }
}
