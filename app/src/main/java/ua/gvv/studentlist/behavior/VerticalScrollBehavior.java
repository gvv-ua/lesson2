package ua.gvv.studentlist.behavior;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by gvv on 23.12.16.
 */

public class VerticalScrollBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
    private final String TAG = "VerticalScrollBehavior";
    private final int SCROLL_NONE = 0;
    private final int SCROLL_DOWN = 1;
    private final int SCROLL_UP = 2;

    private boolean isHidden = false;
    private ViewPropertyAnimatorCompat offsetValueAnimator;
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    public VerticalScrollBehavior() { }

    public VerticalScrollBehavior(Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        int scrollDirection;
        if (dy > 0) {
            scrollDirection = SCROLL_DOWN;
        } else if (dy < 0) {
            scrollDirection = SCROLL_UP;
        } else {
            scrollDirection = SCROLL_NONE;
        }
        handleDirection(child, scrollDirection);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        handleDirection(child, SCROLL_NONE);
    }

    private void animateOffset(final BottomNavigationView child, final int offset) {
        ensureOrCancelAnimator(child);
        offsetValueAnimator.translationY(offset).start();
    }

    private void ensureOrCancelAnimator(BottomNavigationView child) {
        if (offsetValueAnimator == null) {
            offsetValueAnimator = ViewCompat.animate(child);
            offsetValueAnimator.setDuration(300);
            offsetValueAnimator.setInterpolator(INTERPOLATOR);
        } else {
            offsetValueAnimator.cancel();
        }
    }

    private void handleDirection(BottomNavigationView child, int scrollDirection) {
        if ((scrollDirection == SCROLL_UP || scrollDirection == SCROLL_NONE) && isHidden) {
            isHidden = false;
            animateOffset(child, 0);
        } else if (scrollDirection == SCROLL_DOWN && !isHidden) {
            isHidden = true;
            animateOffset(child, child.getHeight());
        }
    }
}
