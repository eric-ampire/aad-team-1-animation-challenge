package com.andela.app.animationchallenge.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import com.andela.app.animationchallenge.R;

public class AppAnimator {

    private Context context;
    public AppAnimator() {
    }

    public AppAnimator(Context context) {
        this.context = context;
    }

    public void fadeAnimation(View view, int resource){
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this.context, resource);
        animatorSet.setTarget(view);
        animatorSet.addListener((Animator.AnimatorListener) this.context);
        animatorSet.start();
    }
}
