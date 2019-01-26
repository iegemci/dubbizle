package com.enesgemci.dubbizle.core.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import static androidx.core.graphics.drawable.DrawableCompat.setTint;
import static androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.create;

public final class NavigationIcon {

    private final int type;
    private final int drawableRes;
    private final Drawable drawable;

    public static NavigationIcon navigationIcon(int type, @DrawableRes int drawableRes) {
        return new NavigationIcon(type, drawableRes, null);
    }

    public static NavigationIcon navigationIcon(int type, @NonNull Drawable drawable) {
        return new NavigationIcon(type, 0, drawable);
    }

    private NavigationIcon(int type, @DrawableRes int drawableRes, Drawable drawable) {
        this.type = type;
        this.drawableRes = drawableRes;
        this.drawable = drawable;
    }

    public Drawable iconDrawable(@NonNull Context context) {
        if (drawable != null) {
            return drawable;
        } else {
            VectorDrawableCompat navIcon = create(context.getResources(), drawableRes, context.getTheme());

            //TODO: Color should get from defaults when moved to library
//            setTint(navIcon, ContextCompat.getColor(context, android.R.color.black));
            return navIcon;
        }
    }

    public int getType() {
        return type;
    }
}
