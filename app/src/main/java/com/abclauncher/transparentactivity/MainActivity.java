package com.abclauncher.transparentactivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int HORIZONTAL_BASE_MARGIN = 40;
    private static final int HORIZONTAL_DIEMN_RANGE = 80;
    private static final int VERTICAL_BASE_MARGIN = 80;
    private static final int VERTICAL_DIMEN_RANGE = 64;
    private View mBorder, mCircleOne, mCircleTwo, mInnerCircleOne, mInnerCircleTwo, mSmallCircle, mThunder;
  /*  private ImageView mImageOne;
    private ImageView mImageTwo;
    private ImageView mImageThree;
    private ImageView mImageFour;*/
    private float density;
    private int[] mCenterPosition = new int[2];
    private int[] mImageOnePosition = new int[2];
    private int[] mImageTwoPosition = new int[2];
    private int[] mImageThreePosition = new int[2];
    private int[] mImageFourPosition = new int[2];
    private View mCenter;
    private int appSize;
    private Random random;
    private FrameLayout mRootView;
    private int mTotalWidth, mTotalHeight;
    private Handler mHandler = new Handler();
    private View mContainer;
    private ObjectAnimator mBorderAnim;
    private View mBgPoint;
    private View mStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        density = getResources().getDisplayMetrics().density;
        mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        mTotalHeight = getResources().getDisplayMetrics().heightPixels;

        mRootView = (FrameLayout) findViewById(R.id.root_view);
        mContainer = findViewById(R.id.container);
        mBorder = findViewById(R.id.optimize_shortcut_border);
        mBgPoint = findViewById(R.id.optimize_shortcut_bg_point);
        mStar = findViewById(R.id.shortcut_star);
        mCircleOne = findViewById(R.id.optimize_shortcut_circle_one);
        mCircleTwo = findViewById(R.id.optimize_shortcut_circle_two);
        mInnerCircleOne = findViewById(R.id.optimize_shortcut_circle_inner_one);
        mInnerCircleTwo = findViewById(R.id.optimize_shortcut_circle_inner_two);
        mSmallCircle = findViewById(R.id.optimize_shortcut_small_circle);
        mThunder = findViewById(R.id.optimize_shortcut_thunder);
        mCenter = findViewById(R.id.center);

        appSize = (int) getResources().getDimension(R.dimen.app_size);


        mCircleTwo.setScaleX(0.5f);
        mCircleTwo.setScaleY(0.5f);
        mInnerCircleOne.setScaleY(0.5f);
        mInnerCircleOne.setScaleX(0.5f);
        startAnim();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startAnim() {

        //step1: scale up and rotate
        ObjectAnimator step1 = ObjectAnimator.ofPropertyValuesHolder(mCircleTwo,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 0.5f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 0.5f, 1f));
        step1.setDuration(1600);
        //step1.setStartDelay(800);
        step1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }


            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mInnerCircleOne.setVisibility(View.VISIBLE);

            }
        });
        step1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PropertyValuesHolder[] propertyValuesHolders = animation.getValues();
                String propertyName = propertyValuesHolders[0].getPropertyName();
                Log.d(TAG, "onAnimationUpdate: " + propertyName);
                float value = (float) animation.getAnimatedValue(propertyName);
                mInnerCircleOne.setScaleX(value);
                mInnerCircleOne.setScaleY(value);
                mInnerCircleTwo.setScaleX(value);
                mInnerCircleTwo.setScaleY(value);
                mSmallCircle.setScaleY(value);
                mSmallCircle.setScaleX(value);

            }
        });
        step1.setInterpolator(new LinearInterpolator());
        //step1.setStartDelay(2000);
        //step1.start();


        ObjectAnimator step2 = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1));
        step2.setDuration(550);
        step2.setStartDelay(1050);

        step2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }


            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mInnerCircleTwo.setVisibility(View.VISIBLE);
                mSmallCircle.setVisibility(View.VISIBLE);

            }
        });
       // step2.setInterpolator(new LinearInterpolator());
        //step2.setStartDelay(2000);
        //step2.start();

        //rotate anim
        ObjectAnimator step3 = ObjectAnimator.ofPropertyValuesHolder(mInnerCircleOne,
                PropertyValuesHolder.ofFloat(View.ROTATION, 0, -360));
        step3.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mInnerCircleOne.setVisibility(View.VISIBLE);
            }
        });
        step3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /*PropertyValuesHolder[] values = animation.getValues();
                String propertyName = values[0].getPropertyName();
                float rotate = (float) animation.getAnimatedValue(propertyName);
                mInnerCircleTwo.setRotation(rotate);*/
            }
        });
        step3.setDuration(600);
        step3.setInterpolator(new LinearInterpolator());
        step3.setRepeatCount(ValueAnimator.INFINITE);
        step3.setRepeatMode(ValueAnimator.RESTART);

        ObjectAnimator step4 = ObjectAnimator.ofPropertyValuesHolder(mThunder,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 0f, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.2f, 0.2f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.2f, 0.2f, 1));
        step4.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mThunder.setAlpha(0);
                mThunder.setScaleX(0.2f);
                mThunder.setScaleY(0.2f);
                mThunder.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCircleOne.setVisibility(View.VISIBLE);
                startBgAnim();
                startAppsAnim();
            }
        });
        step4.setDuration(1600);

        //rotate anim
        ObjectAnimator step5 = ObjectAnimator.ofPropertyValuesHolder(mInnerCircleTwo,
                PropertyValuesHolder.ofFloat(View.ROTATION, 0, -360));
        step5.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        step5.setDuration(1000);
        step5.setStartDelay(300);
        step5.setInterpolator(new LinearInterpolator());
        step5.setRepeatCount(ValueAnimator.INFINITE);
        step5.setRepeatMode(ValueAnimator.RESTART);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(step1, step2, step3, step4, step5);
        animatorSet.setStartDelay(300);
        animatorSet.start();
    }

    private void startAppsAnim() {
        if (random == null){
            random = new Random();
        }
        mCenter.getLocationOnScreen(mCenterPosition);

        for (int i = 0 ; i < 15; i++){
            final int finalI = i;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startImageOneAnim(random.nextInt(4), finalI);
                }
            }, i * 400);
        }

    }


    private void startImageOneAnim(int i, final int currentPos) {
        //i = 3;
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        setImageViewParams(imageView, i);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView,
                PropertyValuesHolder.ofFloat(View.ROTATION, 0f, 360f),
                PropertyValuesHolder.ofFloat(View.ALPHA, 1.f, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.f, 0.6f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.f, 0.6f),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0, getTranslationXByPosition(i)),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0, getTranslationYByPosition(i))
        );
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (currentPos == 14){
                    startScaleAnim();
                }
            }
        });
        Log.d(TAG, "startAppsAnim: ");
        objectAnimator.setDuration(1200);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    //scale dismiss anim
    private void startScaleAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mContainer,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.15f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.15f));
        objectAnimator.setDuration(600);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mContainer.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        objectAnimator.setInterpolator(new LinearInterpolator());
       // objectAnimator.start();

        ObjectAnimator starAnim = ObjectAnimator.ofPropertyValuesHolder(mStar,
                PropertyValuesHolder.ofFloat(View.ROTATION, 0, 180),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 0f));
        starAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mStar.setScaleY(0.5f);
                mStar.setScaleX(0.5f);
                mStar.setVisibility(View.VISIBLE);
            }
        });
        starAnim.setDuration(600);
        starAnim.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator, starAnim);
        animatorSet.start();
    }

    private float getTranslationXByPosition(int i) {
        switch (i){
            case 0:
                return (mCenterPosition[0] - mImageOnePosition[0]);
            case 1:
                return (mCenterPosition[0] - mImageTwoPosition[0]);
            case 2:
                return (mCenterPosition[0] - mImageThreePosition[0]);
            case 3:
                return (mCenterPosition[0] - mImageFourPosition[0]);
        }
        return 0;
    }
    private float getTranslationYByPosition(int i) {
        switch (i){
            case 0:
                return (mCenterPosition[1] - mImageOnePosition[1]);
            case 1:
                return (mCenterPosition[1] - mImageTwoPosition[1]);
            case 2:
                return (mCenterPosition[1] - mImageThreePosition[1]);
            case 3:
                return (mCenterPosition[1] - mImageFourPosition[1]);
        }
         return 0;
    }

    private void setImageViewParams(ImageView imageView, int i) {
        switch (i){
            case 0:
                //location one
                FrameLayout.LayoutParams layoutParamsOne = new FrameLayout.LayoutParams(appSize, appSize);

                //random image_one params
                layoutParamsOne.gravity = Gravity.BOTTOM;
                layoutParamsOne.bottomMargin = (int)(VERTICAL_BASE_MARGIN * density) +
                        random.nextInt((int) (VERTICAL_DIMEN_RANGE * density));
                layoutParamsOne.leftMargin = random.nextInt((int) (HORIZONTAL_BASE_MARGIN * density));
                imageView.setLayoutParams(layoutParamsOne);
                mRootView.addView(imageView);
                imageView.getLocationOnScreen(mImageOnePosition);
                Log.d(TAG, "resetAppsLocationAndCalculateTranslate: one X" + mImageOnePosition[0]);
                Log.d(TAG, "resetAppsLocationAndCalculateTranslate: one X" + mImageOnePosition[1]);
                Log.d(TAG, "resetAppsLocationAndCalculateTranslate: one Y" + (layoutParamsOne.leftMargin));
                Log.d(TAG, "resetAppsLocationAndCalculateTranslate:centerX " + mCenterPosition[0]);
                mImageOnePosition[0] = mImageOnePosition[0] + appSize/2 + layoutParamsOne.leftMargin ;
                mImageOnePosition[1] = mTotalHeight - layoutParamsOne.bottomMargin - appSize/ 2 ;
                break;

            case 1:
                //random image_two params
                FrameLayout.LayoutParams layoutParamsTwo = new FrameLayout.LayoutParams(appSize, appSize);

                layoutParamsTwo.gravity = Gravity.BOTTOM;
                layoutParamsTwo.bottomMargin = random.nextInt((int) (HORIZONTAL_BASE_MARGIN * density));
                layoutParamsTwo.leftMargin = (int)(HORIZONTAL_BASE_MARGIN * density) +
                        random.nextInt((int) (HORIZONTAL_DIEMN_RANGE * density));
                imageView.setLayoutParams(layoutParamsTwo);
                mRootView.addView(imageView);
                imageView.getLocationOnScreen(mImageTwoPosition);
                mImageTwoPosition[0] = mImageTwoPosition[0] + appSize/2 + layoutParamsTwo.leftMargin;
                mImageTwoPosition[1] = mTotalHeight - layoutParamsTwo.bottomMargin - appSize/ 2 ;
                break;
            case 2:
                //random image_three params
                //if (layoutParamsThree == null){
                FrameLayout.LayoutParams layoutParamsThree = new FrameLayout.LayoutParams(appSize, appSize);
                //}
                layoutParamsThree.gravity = Gravity.BOTTOM|Gravity.RIGHT;
                layoutParamsThree.bottomMargin = random.nextInt((int) (HORIZONTAL_BASE_MARGIN * density));
                layoutParamsThree.rightMargin = (int)(HORIZONTAL_BASE_MARGIN * density) +
                        random.nextInt((int) (HORIZONTAL_DIEMN_RANGE * density));
                imageView.setLayoutParams(layoutParamsThree);
                mRootView.addView(imageView);
                imageView.getLocationOnScreen(mImageThreePosition);
                mImageThreePosition[0] = mTotalWidth - layoutParamsThree.rightMargin - appSize/2;
                mImageThreePosition[1] = mTotalHeight - layoutParamsThree.bottomMargin - appSize/ 2;
                break;
            case 3:
                //random image_four params
                FrameLayout.LayoutParams layoutParamsFour = new FrameLayout.LayoutParams(appSize, appSize);

                layoutParamsFour.gravity = Gravity.BOTTOM|Gravity.RIGHT;
                layoutParamsFour.bottomMargin = (int)(VERTICAL_BASE_MARGIN * density) +
                        random.nextInt((int) (VERTICAL_DIMEN_RANGE * density));
                layoutParamsFour.rightMargin = random.nextInt((int) (HORIZONTAL_BASE_MARGIN * density));
                imageView.setLayoutParams(layoutParamsFour);
                mRootView.addView(imageView);
                imageView.getLocationOnScreen(mImageFourPosition);
                mImageFourPosition[0] = mTotalWidth - layoutParamsFour.rightMargin - appSize/2;
                mImageFourPosition[1] = mTotalHeight - layoutParamsFour.bottomMargin - appSize/ 2;
                break;
        }
    }

    //start border anim
    private void startBgAnim() {
        mBorderAnim = ObjectAnimator.ofPropertyValuesHolder(mBorder,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1));
        mBorderAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBorder.setAlpha(0f);
                mBorder.setVisibility(View.VISIBLE);
                mBgPoint.setAlpha(0f);
                mBgPoint.setVisibility(View.VISIBLE);
            }
        });
        mBorderAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PropertyValuesHolder[] values = animation.getValues();
                String propertyName = values[0].getPropertyName();
                float animatedValue = (float) animation.getAnimatedValue(propertyName);
                mBgPoint.setAlpha(animatedValue);
            }
        });
        mBorderAnim.setDuration(600);
        mBorderAnim.setInterpolator(new LinearInterpolator());
        mBorderAnim.setRepeatMode(ValueAnimator.REVERSE);
        mBorderAnim.setRepeatCount(ValueAnimator.INFINITE);
        mBorderAnim.setStartDelay(100);
        mBorderAnim.start();

    }


}
