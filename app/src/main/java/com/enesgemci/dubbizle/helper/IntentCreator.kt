package com.enesgemci.dubbizle.helper

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat

object IntentCreator {

    fun getSharedElementIntent(sharedView: View, activity: Activity): Bundle? {

        val sharedViewPair = Pair.create(
            sharedView,
            ViewCompat.getTransitionName(sharedView)
        )

        val decorView = activity.window.decorView

        val statusBar = decorView.findViewById<View>(android.R.id.statusBarBackground)
        val statusBarPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)

        val navBar = decorView.findViewById<View>(android.R.id.navigationBarBackground)
        val navBarPair = Pair.create(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)

        var activityOptions: ActivityOptionsCompat

        activityOptions = try {
            // somehow this does not work for some devices
            // because statusBar or navBar is null
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                sharedViewPair, statusBarPair, navBarPair
            )
        } catch (e: Exception) {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                sharedView, sharedView.transitionName
            )
        }

        return activityOptions.toBundle()
    }
}
