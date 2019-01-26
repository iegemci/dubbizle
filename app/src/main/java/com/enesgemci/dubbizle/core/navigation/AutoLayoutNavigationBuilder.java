package com.enesgemci.dubbizle.core.navigation;

import android.view.View;
import com.enesgemci.dubbizle.core.navigation.layoutfactory.DummyLayoutFactory;
import com.enesgemci.dubbizle.core.navigation.layoutfactory.IdLayoutFactory;
import com.enesgemci.dubbizle.core.navigation.layoutfactory.LayoutFactory;
import com.enesgemci.dubbizle.core.navigation.layoutfactory.NavigationLayoutFactory;

public final class AutoLayoutNavigationBuilder extends NavigationBuilder<AutoLayoutNavigationBuilder> {

    private boolean includeToolbar;

    public static AutoLayoutNavigationBuilder navigation(int id) {
        return new AutoLayoutNavigationBuilder(new IdLayoutFactory(id));
    }

    public static AutoLayoutNavigationBuilder navigation(View view) {
        return new AutoLayoutNavigationBuilder(new DummyLayoutFactory(view));
    }

    public AutoLayoutNavigationBuilder(LayoutFactory layoutFactory) {
        super(layoutFactory, NavigationDefaultsHolder.navigationDefaults());
    }

    @Override
    protected AutoLayoutNavigationBuilder getThis() {
        return this;
    }

    @Override
    public LayoutFactory getLayoutFactory() {
        return new NavigationLayoutFactory(includeToolbar, super.getLayoutFactory());
    }

    public AutoLayoutNavigationBuilder includeToolbar() {
        this.includeToolbar = true;
        return this;
    }

    public AutoLayoutNavigationBuilder excludeToolbar() {
        this.includeToolbar = false;
        return this;
    }

    public CustomLayoutNavigationBuilder custom() {
        return new CustomLayoutNavigationBuilder(super.getLayoutFactory());
    }
}
