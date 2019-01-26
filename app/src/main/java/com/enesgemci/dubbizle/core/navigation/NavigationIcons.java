package com.enesgemci.dubbizle.core.navigation;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class NavigationIcons extends ArrayList<NavigationIcon> {

    public NavigationIcons(int initialCapacity) {
        super(initialCapacity);
    }

    public NavigationIcons() {
        super();
    }

    public NavigationIcons(Collection<? extends NavigationIcon> c) {
        super(c);
    }

    public boolean contains(int type) {
        return fromType(type) != null;
    }

    @Nullable
    public NavigationIcon fromType(int type) {
        for (NavigationIcon icon : this) {
            if (icon.getType() == type) {
                return icon;
            }
        }

        return null;
    }

}
