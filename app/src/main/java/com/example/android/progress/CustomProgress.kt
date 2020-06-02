package com.example.android.progress

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomProgress @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float
    private var leftColor: Int
    private var rightColor: Int
    private var radius: Float

    private var progressCurrentWidth = 0

    private var leftBarPaint: Paint
    private var rightBarPaint: Paint
    private var backgroundPaint: Paint

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgress)

        try {
            progress = typedArray.getFloat(R.styleable.CustomProgress_customProgress_progress, 0F)
            leftColor = typedArray.getColor(R.styleable.CustomProgress_customProgress_left_color, Color.WHITE)
            rightColor = typedArray.getColor(R.styleable.CustomProgress_customProgress_right_color, Color.BLACK)
            radius = typedArray.getFloat(R.styleable.CustomProgress_customProgress_radius, 0F)

            leftBarPaint = Paint().apply {
                color = leftColor
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            }

            rightBarPaint = Paint().apply {
                color = rightColor
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            }

            backgroundPaint = Paint().apply {
                color = Color.BLUE
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val max = (this.width * progress).toInt()

        canvas.drawRoundRect(0F, 0F, this.width.toFloat(), this.height.toFloat(), radius.px(), radius.px(), backgroundPaint)
        canvas.drawRoundRect(progressCurrentWidth.toFloat(), 0F, this.width.toFloat(), this.height.toFloat(), 0F, 0F, rightBarPaint)
        canvas.drawRoundRect(0F, 0F, progressCurrentWidth.toFloat(), this.height.toFloat(), 0F, 0F, leftBarPaint)

        if (progressCurrentWidth < max) {
            progressCurrentWidth += 10
            invalidate()
        }
    }

    private fun Float.px() = this * context.resources.displayMetrics.density
}