package com.enesgemci.dubbizle.core.navigation;

public final class NavigationDefaultsHolder {

    private static NavigationDefaults defaults;

    private NavigationDefaultsHolder() {
    }

    public static void initDefaults(NavigationDefaults defaults) {
        NavigationDefaultsHolder.defaults = defaults;
    }

    public static NavigationDefaults navigationDefaults() {
        return defaults;
    }
}
