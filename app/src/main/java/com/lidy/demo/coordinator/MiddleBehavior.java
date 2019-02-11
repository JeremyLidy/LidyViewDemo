package com.lidy.demo.coordinator;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

public class MiddleBehavior extends AppBarLayout.Behavior {

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child,
            @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent,
            @NonNull AppBarLayout child, @NonNull View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);

    }
}
