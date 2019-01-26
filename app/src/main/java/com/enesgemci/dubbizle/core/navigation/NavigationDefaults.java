package com.enesgemci.dubbizle.core.navigation;


import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import static java.util.Arrays.asList;

public final class NavigationDefaults {

    private static final View.OnClickListener DUMMY_NAV_ICON_LISTENER = view -> {
    };

    private NavigationIcons navigationIcons = new NavigationIcons();
    private View.OnClickListener navigationIconListener = DUMMY_NAV_ICON_LISTENER;
    private int defaultNavigationIconType;


    public NavigationDefaults navigationIcons(NavigationIcon... navigationIcons) {
        this.navigationIcons.addAll(asList(navigationIcons));
        return this;
    }

    public NavigationDefaults navigationIcon(int type, @DrawableRes int drawableRes) {
        return navigationIcon(NavigationIcon.navigationIcon(type, drawableRes));
    }

    public NavigationDefaults navigationIcon(int type, @NonNull Drawable drawable) {
        return navigationIcon(NavigationIcon.navigationIcon(type, drawable));
    }

    public NavigationDefaults navigationIcon(NavigationIcon navigationIcon) {
        this.navigationIcons.add(navigationIcon);
        return this;
    }

    public NavigationDefaults navigationIconListener(View.OnClickListener listener) {
        this.navigationIconListener = listener == null ? DUMMY_NAV_ICON_LISTENER : listener;
        return this;
    }

    public NavigationDefaults defaultNavigationIconType(int defaultNavigationIconType) {
        this.defaultNavigationIconType = defaultNavigationIconType;
        return this;
    }

    public NavigationIcons navigationIcons() {
        return navigationIcons;
    }

    public View.OnClickListener navigationIconListener() {
        return navigationIconListener;
    }

    public int getDefaultNavigationIconType() {
        return defaultNavigationIconType;
    }

}
