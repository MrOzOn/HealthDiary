package com.mrozon.feature_person.customview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.mrozon.feature_person.R
import timber.log.Timber


class GenderSwitch(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paintChecked : Paint = Paint()
    private val paintUnchecked : Paint = Paint()
    private var mIsMale :  Boolean = true

    private var listener: OnGenderChangeListener? = null

    fun isMale(): Boolean {
        return mIsMale
    }

    fun setMale(male: Boolean) {
        mIsMale = male
        invalidate()
        requestLayout()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GenderSwitch)
        try {
            paintChecked.color = typedArray.getColor(R.styleable.GenderSwitch_colorChecked,
                Color.parseColor("#03A9F4"))
            paintUnchecked.color = typedArray.getColor(R.styleable.GenderSwitch_colorUnchecked,
                Color.parseColor("#E0E0E0"))
            mIsMale = typedArray.getBoolean(R.styleable.GenderSwitch_isMale, true)
        } finally {
            typedArray.recycle()
        }
    }

    fun setOnGenderChangeListener(listener: OnGenderChangeListener){
        this.listener = listener
    }

    private val paintActive : Paint = Paint().apply {
    }

    private val paintInactive : Paint = Paint().apply {
        alpha = 75
    }

    private var bitmapMale:Bitmap? = null
    private var bitmapFemale:Bitmap? = null
    private var sizeBitmap = 0

    private var maleXPosition: Float = 0f
    private var femaleXPosition: Float = 0f
    private var currentXPosition: Float = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Timber.d("onMeasure")

        val heightSize = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val widthSize = (MeasureSpec.getSize(widthMeasureSpec) - 2 * paddingStart - 2 * paddingEnd) / 2.5
        sizeBitmap = minOf(heightSize, widthSize.toInt())
        bitmapMale = getBitmapFromVectorDrawable(context, R.drawable.ic_male_avatar,
            sizeBitmap)
        bitmapFemale = getBitmapFromVectorDrawable(context, R.drawable.ic_female_avatar,
            sizeBitmap)
        maleXPosition = sizeBitmap.toFloat()+paddingStart+paddingEnd
        femaleXPosition = MeasureSpec.getSize(widthMeasureSpec) - maleXPosition
        if(mIsMale)
            currentXPosition = 0f
        else
            currentXPosition = maleXPosition
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Timber.d("onLayout")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        Timber.d("onDraw")
        super.onDraw(canvas)

        val top = (height - sizeBitmap)/2F
        val widthCheckedArea = width-(sizeBitmap+paddingStart+paddingEnd*1F)

        canvas.apply {
            drawRect(0f,0f,width.toFloat(), height.toFloat(), paintUnchecked)
            drawBitmap(bitmapMale!!,(paddingStart+paddingEnd)/2f,top,paintInactive)
            drawBitmap(bitmapFemale!!,widthCheckedArea + (paddingStart+paddingEnd)/2F,top,paintInactive)
            drawRect(currentXPosition, 0f, currentXPosition+widthCheckedArea, height.toFloat(), paintChecked)

            var bitmap = bitmapMale!!
            if(!mIsMale)
                bitmap = bitmapFemale!!
            drawBitmap(bitmap,currentXPosition + (widthCheckedArea-sizeBitmap)/2,top,paintActive)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int, width:Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context!!, drawableId)
        val bitmap = Bitmap.createBitmap(
            width,
            width,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    private val myListener =  object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            Timber.d("onDown: $e")
            return true
        }
    }
    private val detector: GestureDetector = GestureDetector(context, myListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event).let { result ->
            if (!result) {
                if (event?.action == MotionEvent.ACTION_UP) {
//                    Timber.d("ACTION_UP: $event")
                    mIsMale = !mIsMale
                    listener?.onMaleSelected(mIsMale)
                    val startValueAnimator = currentXPosition
                    var endValueAnimator = 0f
                    if(!mIsMale){
//                        startValueAnimator = 0f
                        endValueAnimator = maleXPosition
                    }
                    val valueAnimator = ValueAnimator.ofFloat(startValueAnimator, endValueAnimator).apply {
//                        duration = 1000
                        addUpdateListener {
                            currentXPosition = it.animatedValue as Float
//                            Timber.d("animatedValue=${it.animatedValue}")
                            invalidate()
                        }
                    }
                    valueAnimator.start()
                    true
                } else false
            } else true
        }
    }

    interface OnGenderChangeListener {
        fun onMaleSelected(male: Boolean)
    }

}