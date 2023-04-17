package com.example.downloadprogressapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import kotlin.math.min

class RoundedGifView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GifImageView(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = true }
    private val path = Path()
    private var cornerRadius = 0f

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedGifView,
            defStyleAttr,
            0
        )

        cornerRadius = typedArray.getDimension(
            R.styleable.RoundedGifView_cornerRadius,
            0f
        )

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable

        if (drawable is GifDrawable) {
            drawable.setBounds(0, 0, width, height)
        }

        if (cornerRadius > 0f) {
            path.reset()
            path.addRoundRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                cornerRadius,
                cornerRadius,
                Path.Direction.CW
            )
            canvas.clipPath(path)
        }

        super.onDraw(canvas)

        if (cornerRadius > 0f) {
            canvas.save()
            canvas.restore()
        }
    }

    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = min(cornerRadius, width.toFloat() / 2f)
        invalidate()
    }
}
