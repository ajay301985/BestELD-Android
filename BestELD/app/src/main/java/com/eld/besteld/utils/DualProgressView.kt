package com.eld.besteld.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.eld.besteld.R

class DualProgressView : View {
    /**
     * Draw outer progress
     */
    private var mOuterCirclePaint: Paint? = null

    /**
     * Draw inner progress
     */
    private var mInnerCirclePaint: Paint? = null

    /**
     * Thickness of the progress
     */
    private var mThickness = 0f

    /**
     * Padding between the two circles
     */
    private var mInnerPadding = 0f

    /**
     * Animation duration
     */
    private var mAnimDuration = 0

    /**
     * Rect for drawing outer circle
     */
    private var mOuterCircleRect: RectF? = null

    /**
     * Rect for drawing inner circle
     */
    private var mInnerCircleRect: RectF? = null

    /**
     * Outer Circle Color
     */
    @ColorInt
    private var mOuterCircleColor = 0

    /**
     * Inner Circle Color
     */
    @ColorInt
    private var mInnerCircleColor = 0

    /**
     * Number of step in the Animation
     */
    private var mSteps = 0

    /**
     * Actual size of the complete circle.
     */
    private var mSize = 0

    /**
     * Starting Angle to start the progress Animation.
     */
    private var mStartAngle = 0f

    /***
     * Sweep Angle
     */
    private var mIndeterminateSweep = 0f

    /**
     * Rotation offset
     */
    private var mIndeterminateRotateOffset = 0f

    /**
     * Progress Animation set
     */
    private var mIndeterminateAnimator: AnimatorSet? = null

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    /**
     * Initialize all drawing parameters from the custom Attributes.
     */
    protected fun init(attrs: AttributeSet?, defStyle: Int) {
        mOuterCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mInnerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mOuterCircleRect = RectF()
        mInnerCircleRect = RectF()
        val resources: Resources = resources
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.DualProgressView, defStyle, 0
        )
        mThickness = a.getDimensionPixelSize(
            R.styleable.DualProgressView_dpv_thickness,
            resources.getDimensionPixelSize(R.dimen._3sdp)
        ).toFloat()
        mInnerPadding = a.getDimensionPixelSize(
            R.styleable.DualProgressView_dpv_inner_padding,
            resources.getDimensionPixelSize(R.dimen._8sdp)
        ).toFloat()
        mOuterCircleColor = a.getColor(
            R.styleable.DualProgressView_dpv_inner_color,
            ContextCompat.getColor(context, R.color.purple_700)
        )
        mInnerCircleColor = a.getColor(
            R.styleable.DualProgressView_dpv_outer_color,
            ContextCompat.getColor(context, android.R.color.holo_red_dark)
        )
        mAnimDuration = a.getInteger(
            R.styleable.DualProgressView_dpv_anim_duration,
            3000
        )
        mSteps = 3
        mStartAngle = -90f
        a.recycle()
        setPaint()
    }

    /**
     * Set the two paint object with
     * supplied color for drawing.
     */
    private fun setPaint() {
        mOuterCirclePaint?.let {
            it.color = mOuterCircleColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = mThickness
            it.strokeCap = Paint.Cap.BUTT
        }
        mInnerCirclePaint?.let {
            it.color = mInnerCircleColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = mThickness
            it.strokeCap = Paint.Cap.BUTT
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Draw outer circle progress
        mOuterCircleRect?.let { outerCircleRect ->
            mOuterCirclePaint?.let { it ->
                canvas.drawArc(
                    outerCircleRect , mStartAngle + mIndeterminateRotateOffset,
                    mIndeterminateSweep, false, it
                )
            }
        }
        //Draw inner circle progress
        mInnerCircleRect?.let { innerCircleRect ->
            mInnerCirclePaint?.let { it ->
                canvas.drawArc(
                    innerCircleRect, mStartAngle + mIndeterminateRotateOffset + 180f,
                    mIndeterminateSweep, false, it
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val xPad = paddingLeft + paddingRight
        val yPad = paddingTop + paddingBottom
        val width = measuredWidth - xPad
        val height = measuredHeight - yPad
        mSize = if (width < height) width else height
        setMeasuredDimension(mSize + xPad, mSize + yPad)
        updateRectAngleBounds()
    }

    /**
     * Set two rectangle bounds for drawing two circles.
     */
    private fun updateRectAngleBounds() {
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        mOuterCircleRect!![paddingLeft + mThickness, paddingTop + mThickness, mSize - paddingLeft - mThickness] =
            mSize - paddingTop - mThickness
        mInnerCircleRect!![paddingLeft + mThickness + mInnerPadding, paddingTop + mThickness + mInnerPadding, mSize - paddingLeft - mThickness - mInnerPadding] =
            mSize - paddingTop - mThickness - mInnerPadding
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSize = if (w < h) w else h
        updateRectAngleBounds()
    }

    /**
     * Create the Circle Progress Animation sequence
     */
    private fun createIndeterminateAnimator(step: Float): AnimatorSet {
        val maxSweep =
            360f * (mSteps - 1) / mSteps + INDETERMINANT_MIN_SWEEP
        val start =
            -90f + step * (maxSweep - INDETERMINANT_MIN_SWEEP)

        // Extending the front of the arc
        val frontEndExtend = ValueAnimator.ofFloat(
            INDETERMINANT_MIN_SWEEP,
            maxSweep
        )
        frontEndExtend.duration = mAnimDuration / mSteps / 2.toLong()
        frontEndExtend.interpolator = DecelerateInterpolator(1f)
        frontEndExtend.addUpdateListener { animation ->
            mIndeterminateSweep = animation.animatedValue as Float
            invalidate()
        }
        frontEndExtend.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                frontEndExtend.removeAllListeners()
                frontEndExtend.cancel()
            }
        })

        // Overall rotation
        val rotateAnimator1 = ValueAnimator.ofFloat(
            step * 720f / mSteps,
            (step + .5f) * 720f / mSteps
        )
        rotateAnimator1.duration = mAnimDuration / mSteps / 2.toLong()
        rotateAnimator1.interpolator = LinearInterpolator()
        rotateAnimator1.addUpdateListener { animation ->
            mIndeterminateRotateOffset = animation.animatedValue as Float
        }
        rotateAnimator1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                rotateAnimator1.removeAllListeners()
                rotateAnimator1.cancel()
            }
        })

        // Followed by...

        // Retracting the back end of the arc
        val backEndRetract = ValueAnimator.ofFloat(
            start,
            start + maxSweep - INDETERMINANT_MIN_SWEEP
        )
        backEndRetract.duration = mAnimDuration / mSteps / 2.toLong()
        backEndRetract.interpolator = DecelerateInterpolator(1f)
        backEndRetract.addUpdateListener { animation ->
            mStartAngle = animation.animatedValue as Float
            mIndeterminateSweep = maxSweep - mStartAngle + start
            invalidate()
            if (mStartAngle > RESETTING_ANGLE) {
                resetAnimation()
            }
        }
        backEndRetract.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                backEndRetract.cancel()
                backEndRetract.removeAllListeners()
            }
        })

        // More overall rotation
        val rotateAnimator2 = ValueAnimator.ofFloat(
            (step + .5f) * 720f / mSteps,
            (step + 1) * 720f / mSteps
        )
        rotateAnimator2.duration = mAnimDuration / mSteps / 2.toLong()
        rotateAnimator2.interpolator = LinearInterpolator()
        rotateAnimator2.addUpdateListener { animation ->
            mIndeterminateRotateOffset = animation.animatedValue as Float
        }
        rotateAnimator2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                rotateAnimator2.removeAllListeners()
                rotateAnimator2.cancel()
            }
        })
        val set = AnimatorSet()
        set.play(frontEndExtend).with(rotateAnimator1)
        set.play(backEndRetract).with(rotateAnimator2).after(rotateAnimator1)
        return set
    }

    private fun resetAnimation() {
        mStartAngle = -90f
        if (mIndeterminateAnimator != null && mIndeterminateAnimator!!.isRunning) {
            mIndeterminateAnimator!!.cancel()
        }
        mIndeterminateSweep = INDETERMINANT_MIN_SWEEP

        // Build the whole AnimatorSet
        mIndeterminateAnimator = AnimatorSet()
        var prevSet: AnimatorSet? = null
        var nextSet: AnimatorSet
        for (k in 0 until mSteps) {
            nextSet = createIndeterminateAnimator(k.toFloat())
            val builder =
                mIndeterminateAnimator!!.play(nextSet)
            if (prevSet != null) {
                builder.after(prevSet)
            }
            prevSet = nextSet
        }
        mIndeterminateAnimator!!.start()
    }

    /**
     * Starts the progress bar animation.
     * (This is an alias of resetAnimation() so it does the same thing.)
     */
    private fun startAnimation() {
        resetAnimation()
    }

    /**
     * Stops the animation
     */
    private fun stopAnimation() {
        if (mIndeterminateAnimator != null) {
            mIndeterminateAnimator!!.cancel()
            mIndeterminateAnimator!!.removeAllListeners()
            mIndeterminateAnimator = null
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    override fun setVisibility(visibility: Int) {
        val currentVisibility = getVisibility()
        super.setVisibility(visibility)
        if (visibility != currentVisibility) {
            if (visibility == VISIBLE) {
                resetAnimation()
            } else if (visibility == GONE || visibility == INVISIBLE) {
                stopAnimation()
            }
        }
    }

    companion object {
        private const val INDETERMINANT_MIN_SWEEP = 15f
        const val RESETTING_ANGLE = 620
    }
}