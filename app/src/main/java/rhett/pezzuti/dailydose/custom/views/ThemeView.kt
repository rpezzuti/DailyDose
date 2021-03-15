package rhett.pezzuti.dailydose.custom.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import rhett.pezzuti.dailydose.R

private enum class ThemeStyle() {
    DEFAULT,
    ALT,
    THREE
}

class ThemeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.primaryColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawRect(100f, 100f, 100f, 100f, rectPaint.apply {
            color = context.getColor(R.color.primaryColor)
        })
        canvas.drawRect(50f, 50f, 50f, 50f, rectPaint.apply {
            color = context.getColor(R.color.secondaryColor)
        })




    }




}