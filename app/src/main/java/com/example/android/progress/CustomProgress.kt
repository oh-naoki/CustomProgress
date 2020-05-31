package com.example.android.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.View

class CustomProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float
    private var progressCurrentWidth = 0

    private var progressDrawable: ShapeDrawable
    private var backgroundDrawable: ShapeDrawable

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgress)

        try {
            progress = typedArray.getFloat(R.styleable.CustomProgress_customProgress_progress, 0F)
        } finally {
            typedArray.recycle()
        }

        val noRadius = floatArrayOf(0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F)
        progressDrawable = ShapeDrawable(RoundRectShape(noRadius, null, null)).apply {
            paint.color = Color.WHITE
        }

        backgroundDrawable = ShapeDrawable(RoundRectShape(noRadius, null, null)).apply {
            paint.color = Color.BLACK
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val max = (this.width * progress).toInt()

        backgroundDrawable.setBounds(
            progressCurrentWidth,
            0,
            this.width,
            this.height
        )
        backgroundDrawable.draw(canvas)

        progressDrawable.setBounds(
            0,
            0,
            progressCurrentWidth,
            this.height
        )
        progressDrawable.draw(canvas)

        if (progressCurrentWidth < max) {
            progressCurrentWidth += 10
            invalidate()
        }
    }
}