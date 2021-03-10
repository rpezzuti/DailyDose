package rhett.pezzuti.dailydose.custom.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import rhett.pezzuti.dailydose.R
import kotlin.math.min

private enum class SmileColor() {
    RED,
    GREEN,
    BLUE,
    PURPLE;

    fun next() = when (this) {
        RED -> GREEN
        GREEN -> BLUE
        BLUE -> PURPLE
        PURPLE -> RED
    }
}

class SmileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    // The radius of the circle
    private var radius = 0.0f
    private var circleColor = SmileColor.RED

    // Happy Face Variables.
    private var eyeRadius = 0.0f
    private var eyeYPosition = 0.0f
    private var leftEyeXPosition = 0.0f
    private var rightEyeXPosition = 0.0f

    // Failed Happy face oval
    // private val oval : RectF = RectF()


    init {
        isClickable = true
    }


    // Make paint object.
    // Variables are initialized here for speed purposes. Prevent visual stutter.
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 27.5f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val facePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10.0f
        color = Color.BLACK
    }


    override fun performClick(): Boolean {
        // Allows onClick() to be overridden separately.
        if (super.performClick()) return true

        circleColor = circleColor.next()

        invalidate()
        return true
    }

    // Width and height of the view defined by XML
    // Called before onDraw()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
        eyeRadius = (min(width, height) / 15.0f)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        circlePaint.color = when (circleColor) {
            SmileColor.RED -> Color.RED
            SmileColor.GREEN -> Color.GREEN
            SmileColor.BLUE -> Color.BLUE
            SmileColor.PURPLE -> Color.MAGENTA
        }

        eyeYPosition = ((height/2)*0.8f)
        leftEyeXPosition = (width/4.0f)
        rightEyeXPosition = ((width/4.0f) + (width/2.0f))


        // Draw the circle
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), radius, circlePaint)

        // Draw the text in Black after circle is drawn.
        val clickText = resources.getString(R.string.text_click_me)
        canvas.drawText(clickText, width / 2.0f, height * 0.075f, circlePaint.apply {
            color = Color.BLACK
        })



        if (circleColor == SmileColor.PURPLE) {

            // Left Eye
            canvas.drawCircle(leftEyeXPosition, eyeYPosition, eyeRadius, facePaint)
            // Right Eye
            canvas.drawCircle(rightEyeXPosition, eyeYPosition, eyeRadius, facePaint)


            /**
             * Failed Happy face
            RectF oval = new RectF(leftEyeXPosition, yPosition + yPosition / 8, rightEyeXPosition, (float) (yPosition + yPosition / 2.5)); // left top right bottom
            oval.set(leftEyeXPosition, (height / 2.0f + ((height / 2.0f) * 0.8f)), rightEyeXPosition, ((height / 2.0f) + ((height / 2.0f) / 2.5f)))
            canvas.drawArc(oval, 10.0f, 150.0f, false, facePaint)
            **/
            canvas.drawLine(leftEyeXPosition, height / 1.8f, rightEyeXPosition, height / 1.8f, facePaint)
        }
    }
}