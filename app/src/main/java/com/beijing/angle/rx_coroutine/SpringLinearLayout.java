package com.beijing.angle.rx_coroutine;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * @author 刘红鹏
 * @description:
 * @date: 2026/1/4 14:06
 * @Link: https://github.com/AngleCoding
 */

public class SpringLinearLayout extends LinearLayout {
    private SpringAnimation scaleXAnimation;
    private SpringAnimation scaleYAnimation;

    public SpringLinearLayout(Context context) {
        super(context);
        init();
    }

    public SpringLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化X轴缩放动画
        scaleXAnimation = new SpringAnimation(this, SpringAnimation.SCALE_X, 1.0f);
        SpringForce springForceX = new SpringForce(1.0f);
        springForceX.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        scaleXAnimation.setSpring(springForceX);

        // 初始化Y轴缩放动画
        scaleYAnimation = new SpringAnimation(this, SpringAnimation.SCALE_Y, 1.0f);
        SpringForce springForceY = new SpringForce(1.0f);
        springForceY.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceY.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        scaleYAnimation.setSpring(springForceY);
    }

    public void animateScale(float scale) {
        scaleXAnimation.animateToFinalPosition(scale);
        scaleYAnimation.animateToFinalPosition(scale);
    }

    public void resetScale() {
        scaleXAnimation.animateToFinalPosition(1.0f);
        scaleYAnimation.animateToFinalPosition(1.0f);
    }
}
