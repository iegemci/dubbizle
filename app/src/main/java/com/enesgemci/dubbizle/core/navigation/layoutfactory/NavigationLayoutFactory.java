package com.enesgemci.dubbizle.core.navigation.layoutfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.enesgemci.dubbizle.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.LinearLayout.VERTICAL;

public final class NavigationLayoutFactory implements LayoutFactory {

    private final boolean includeToolbar;
    private final LayoutFactory origin;

    public NavigationLayoutFactory(boolean includeToolbar, LayoutFactory origin) {
        this.includeToolbar = includeToolbar;
        this.origin = origin;
    }

    @Override
    public View produceLayout(LayoutInflater inflater, @Nullable ViewGroup container) {

        LinearLayout parent = new LinearLayout(inflater.getContext());
        parent.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parent.setOrientation(VERTICAL);

        View child = origin.produceLayout(inflater, parent);
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        if (includeToolbar) {
            inflater.inflate(R.layout.layout_toolbar_default, parent);
        }
        parent.addView(child, childParams);

        return parent;
    }
}
