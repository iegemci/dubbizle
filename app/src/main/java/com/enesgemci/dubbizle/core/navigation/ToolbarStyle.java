package com.enesgemci.dubbizle.core.navigation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ToolbarStyle {

    @IntDef({
            NavigationIconType.NONE,
            NavigationIconType.BACK,
            NavigationIconType.CLOSE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationIconType {
        int NONE = 0;
        int BACK = 1;
        int CLOSE = 2;
    }

}
