package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.InfinitePagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class CustomImageSlider extends SliderLayout {
    private static final String TAG = "CustomImageSlider";
    private HackyViewPager mViewPager;

    private Context mContext;

    /**
     * InfiniteViewPager adapter.
     */
    private SliderAdapter mSliderAdapter;

    /**
     * {@link com.daimajia.slider.library.Tricks.ViewPagerEx} indicator.
     */
    private PagerIndicator mIndicator;


    /**
     * A timer and a TimerTask using to cycle the {@link com.daimajia.slider.library.Tricks.ViewPagerEx}.
     */
    private Timer mCycleTimer;
    private TimerTask mCycleTask;

    /**
     * For resuming the cycle, after user touch or click the {@link com.daimajia.slider.library.Tricks.ViewPagerEx}.
     */
    private Timer mResumingTimer;
    private TimerTask mResumingTask;

    /**
     * If {@link com.daimajia.slider.library.Tricks.ViewPagerEx} is Cycling
     */
    private boolean mCycling;

    /**
     * Determine if auto recover after user touch the {@link com.daimajia.slider.library.Tricks.ViewPagerEx}
     */
    private boolean mAutoRecover = true;

    private int mTransformerId;

    /**
     * {@link com.daimajia.slider.library.Tricks.ViewPagerEx} transformer time span.
     */
    private int mTransformerSpan = 1100;

    private boolean mAutoCycle;

    /**
     * the duration between animation.
     */
    private long mSliderDuration = 4000;

    /**
     * Visibility of {@link com.daimajia.slider.library.Indicators.PagerIndicator}
     */
    private PagerIndicator.IndicatorVisibility mIndicatorVisibility = PagerIndicator.IndicatorVisibility.Visible;

    /**
     * {@link com.daimajia.slider.library.Tricks.ViewPagerEx} 's transformer
     */
    private BaseTransformer mViewPagerTransformer;

    /**
     * @see com.daimajia.slider.library.Animations.BaseAnimationInterface
     */
    private BaseAnimationInterface mCustomAnimation;

    public CustomImageSlider(Context context) {
        super(context);
    }

    public CustomImageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(com.daimajia.slider.library.R.layout.slider_layout, this, true);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, com.daimajia.slider.library.R.styleable.SliderLayout,
                defStyle,0);

        mTransformerSpan = attributes.getInteger(com.daimajia.slider.library.R.styleable.SliderLayout_pager_animation_span, 1100);
        mTransformerId = attributes.getInt(com.daimajia.slider.library.R.styleable.SliderLayout_pager_animation, Transformer.Default.ordinal());
        mAutoCycle = attributes.getBoolean(com.daimajia.slider.library.R.styleable.SliderLayout_auto_cycle,true);
        int visibility = attributes.getInt(com.daimajia.slider.library.R.styleable.SliderLayout_indicator_visibility,0);
        for(PagerIndicator.IndicatorVisibility v: PagerIndicator.IndicatorVisibility.values()){
            if(v.ordinal() == visibility){
                mIndicatorVisibility = v;
                break;
            }
        }
        mSliderAdapter = new SliderAdapter(mContext);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

        mViewPager = (HackyViewPager) findViewById(com.daimajia.slider.library.R.id.daimajia_slider_viewpager);
        mViewPager.setAdapter(wrappedAdapter);

        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        recoverCycle();
                        break;
                }
                return false;
            }



        });

        attributes.recycle();
        setPresetIndicator(PresetIndicators.Center_Bottom);
        setPresetTransformer(mTransformerId);
        setSliderTransformDuration(mTransformerSpan,null);
        setIndicatorVisibility(mIndicatorVisibility);
        if(mAutoCycle){
            startAutoCycle();
        }
    }

    /**
     * when paused cycle, this method can weak it up.
     */
    private void recoverCycle(){
        if(!mAutoRecover || !mAutoCycle){
            return;
        }

        if(!mCycling){
            if(mResumingTask != null && mResumingTimer!= null){
                mResumingTimer.cancel();
                mResumingTask.cancel();
            }
            mResumingTimer = new Timer();
            mResumingTask = new TimerTask() {
                @Override
                public void run() {
                    startAutoCycle();
                }
            };
            mResumingTimer.schedule(mResumingTask, 6000);
        }
    }
}