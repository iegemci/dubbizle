package com.enesgemci.dubbizle.core.navigation;

import android.graphics.drawable.Drawable;
import com.enesgemci.dubbizle.R;
import com.enesgemci.dubbizle.core.navigation.layoutfactory.LayoutFactory;
import com.enesgemci.dubbizle.core.navigation.menu.MenuActions;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationBuilder<T extends NavigationBuilder<T>> {

    public static final int NO_NAV_ICON = -1;

    private final LayoutFactory layoutFactory;
    private final NavigationDefaults navigationDefaults;

    int toolbarId = R.id.toolbar;

    private int toolbarNavigationIcon;
    private int toolbarTitleRes;
    private CharSequence toolbarTitle;
    private int toolbarSubtitleRes;
    private CharSequence toolbarSubtitle;
    private int toolbarLogoRes;
    private Drawable toolbarLogo;

    private List<Integer> menuRes = new ArrayList<>();
    private MenuActions.Builder menuActions = new MenuActions.Builder();

    NavigationBuilder(LayoutFactory layoutFactory, NavigationDefaults navigationDefaults) {
        this.layoutFactory = layoutFactory;
        this.navigationDefaults = navigationDefaults;
        this.toolbarNavigationIcon = navigationDefaults.getDefaultNavigationIconType();
    }

    protected abstract T getThis();

    public T toolbarNavigationIcon(int toolbarNavigationIcon) {
        if (!navigationDefaults.navigationIcons().contains(toolbarNavigationIcon)
                && toolbarNavigationIcon != NO_NAV_ICON) {
            throw new IllegalArgumentException("There is no navigation icon for type: " + toolbarNavigationIcon);
        }

        this.toolbarNavigationIcon = toolbarNavigationIcon;
        return getThis();
    }

    public T setToolbarTitleRes(int toolbarTitleRes) {
        this.toolbarTitleRes = toolbarTitleRes;
        return getThis();
    }

    public T setToolbarTitle(CharSequence toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
        return getThis();
    }

    public T setToolbarSubtitleRes(int toolbarSubtitleRes) {
        this.toolbarSubtitleRes = toolbarSubtitleRes;
        return getThis();
    }

    public T setToolbarSubtitle(CharSequence toolbarSubtitle) {
        this.toolbarSubtitle = toolbarSubtitle;
        return getThis();
    }

    public T setToolbarLogoRes(int toolbarLogoRes) {
        this.toolbarLogoRes = toolbarLogoRes;
        return getThis();
    }

    public T setToolbarLogo(Drawable toolbarLogo) {
        this.toolbarLogo = toolbarLogo;
        return getThis();
    }

    public T addMenuRes(int menuRes, MenuActions.MenuActionItem... items) {
        return addMenuRes(menuRes, new MenuActions.Builder(items));
    }

    public T addMenuRes(int menuRes, MenuActions.Builder menuBuilder) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuBuilder);
        return getThis();
    }

    public T addMenuRes(int menuRes, MenuActions menuActions) {
        this.menuRes.add(menuRes);
        this.menuActions.append(menuActions);
        return getThis();
    }

    public LayoutFactory getLayoutFactory() {
        return layoutFactory;
    }

    public NavigationDefaults getNavigationDefaults() {
        return navigationDefaults;
    }

    public int getToolbarTitleRes() {
        return toolbarTitleRes;
    }

    public CharSequence getToolbarTitle() {
        return toolbarTitle;
    }

    public int getToolbarSubtitleRes() {
        return toolbarSubtitleRes;
    }

    public CharSequence getToolbarSubtitle() {
        return toolbarSubtitle;
    }

    public int getToolbarLogoRes() {
        return toolbarLogoRes;
    }

    public Drawable getToolbarLogo() {
        return toolbarLogo;
    }

    public int getToolbarNavigationIcon() {
        return toolbarNavigationIcon;
    }

    public List<Integer> getMenuRes() {
        return menuRes;
    }

    public MenuActions.Builder getMenuActions() {
        return menuActions;
    }

    public int getToolbarId() {
        return toolbarId;
    }
}
