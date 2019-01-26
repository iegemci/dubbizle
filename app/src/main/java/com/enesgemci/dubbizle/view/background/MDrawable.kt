/*
 * Copyright (c) 2017.
 *
 *     "Therefore those skilled at the unorthodox
 *      are infinite as heaven and earth,
 *      inexhaustible as the great rivers.
 *      When they come to an end,
 *      they begin again,
 *      like the days and months;
 *      they die and are reborn,
 *      like the four seasons."
 *
 * - Sun Tsu, "The Art of War"
 *
 * Enes Gemci
 */

package io.kambo.core.util.background

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.GradientDrawable.Orientation.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.extension.dpToPx
import java.util.*

/**
 * Created by enesgemci on 17/02/2017.
 */
class MDrawable private constructor(context: Context) {

    var pressedColor: Int = 0
    var backgroundColor: Int
    var borderColor: Int = 0
    var topLeftRadius: Float = 0f
    var topRightRadius: Float = 0f
    var bottomRightRadius: Float = 0f
    var bottomLeftRadius: Float = 0f
    var types: MutableList<Type>
    var shape: Shape
    var borderWidth: Float
    var gradientOrientation: GradientDrawable.Orientation? = null
    var gradientEndColor: Int = 0

    init {
        val typedValue = TypedValue()

        pressedColor = try {
            if (context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                ContextCompat.getColor(context, R.color.colorAccent)
            }
        } catch (e: Exception) {
            ContextCompat.getColor(context, R.color.colorAccent)
        }

        backgroundColor = -1
        shape = Shape.RECTANGLE
        types = ArrayList(2)
        borderWidth = 1f.dpToPx(context)
    }

    internal fun get(): Drawable {
        val drawables = ArrayList<Drawable>()
        val outerRadii = floatArrayOf(
            topLeftRadius,
            topLeftRadius,
            topRightRadius,
            topRightRadius,
            bottomRightRadius,
            bottomRightRadius,
            bottomLeftRadius,
            bottomLeftRadius
        )

        types.forEach {
            when (it) {
                MDrawable.Type.BACKGROUND -> {
                    val shapeDrawable =
                        getDrawable(shape, backgroundColor, outerRadii, gradientEndColor, gradientOrientation)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        drawables.add(MRippleDrawable(ColorStateList.valueOf(pressedColor), shapeDrawable, null))
                    } else {
                        val pressed =
                            getDrawable(shape, pressedColor, outerRadii, gradientEndColor, gradientOrientation)

                        val stateListDrawable = StateListDrawable()
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressed)
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), pressed)
                        stateListDrawable.addState(intArrayOf(android.R.attr.state_activated), pressed)
                        stateListDrawable.addState(intArrayOf(), shapeDrawable)

                        drawables.add(stateListDrawable)
                    }
                }
                MDrawable.Type.BORDER -> drawables.add(
                    getBorderDrawable(
                        shape,
                        borderColor,
                        outerRadii,
                        borderWidth,
                        gradientEndColor,
                        gradientOrientation
                    )
                )
            }
        }

        return LayerDrawable(drawables.toTypedArray())
    }

    class Builder(val context: Context) {

        private var mDrawable: MDrawable = MDrawable(context)

        /**
         * @param topLeftRadius top left corner radius of view
         */
        fun setTopLeftRadius(topLeftRadius: Float): Builder {
            mDrawable.topLeftRadius = topLeftRadius
            return this
        }

        /**
         * @param topRightRadius top right corner of view
         */
        fun setTopRightRadius(topRightRadius: Float): Builder {
            mDrawable.topRightRadius = topRightRadius
            return this
        }

        /**
         * @param bottomRightRadius bottom right corner radius of view
         */
        fun setBottomRightRadius(bottomRightRadius: Float): Builder {
            mDrawable.bottomRightRadius = bottomRightRadius
            return this
        }


        /**
         * @param bottomLeftRadius bottom left corner radius of view
         */
        fun setBottomLeftRadius(bottomLeftRadius: Float): Builder {
            mDrawable.bottomLeftRadius = bottomLeftRadius
            return this
        }

        /**
         * @param radius corners radius of view
         */
        fun setRadius(radius: Float): Builder {
            mDrawable.topLeftRadius = radius
            mDrawable.topRightRadius = radius
            mDrawable.bottomLeftRadius = radius
            mDrawable.bottomRightRadius = radius
            return this
        }

        /**
         * @param type drawable type.
         * *
         *
         *see [Type]
         */
        fun addType(type: Type): Builder {
            if (!mDrawable.types.contains(type)) {
                mDrawable.types.add(type)
            }
            return this
        }

        /**
         * @param types drawable types.
         * *
         *
         *see [Type]
         */
        fun addType(types: List<Type>): Builder {
            types
                .filterNot { mDrawable.types.contains(it) }
                .forEach { mDrawable.types.add(it) }
            return this
        }

        /**
         * @param width with of border background
         */
        fun setBorderWidth(width: Float): Builder {
            mDrawable.borderWidth = width
            return this
        }

        /**
         * @param shape ic_plane of drawable.
         * *
         *
         *see [Shape]
         */
        fun setShape(shape: Shape): Builder {
            mDrawable.shape = shape
            return this
        }

        /**
         * @param orientation orientation of gradient drawable.
         * *
         *
         *see [GradientDrawable.Orientation]
         */
        fun setGradientOrientation(orientation: Int): Builder {
            mDrawable.gradientOrientation = getGradientOrientation(orientation)
            return this
        }

        /**
         * Creates default background drawable
         * if [Build.VERSION] Lollipop or greater, then creates [MRippleDrawable]
         * otherwise creates [StateListDrawable]
         */
        fun build(): Drawable = mDrawable.get()

        /**
         * @return [MDrawable]
         */
        fun raw(): MDrawable = mDrawable

        private fun getGradientOrientation(i: Int): GradientDrawable.Orientation =
            when (i) {
                0 -> BL_TR
                1 -> BOTTOM_TOP
                2 -> BR_TL
                3 -> LEFT_RIGHT
                4 -> RIGHT_LEFT
                5 -> TL_BR
                6 -> TOP_BOTTOM
                7 -> TR_BL
                else -> BOTTOM_TOP
            }

        /**
         * @param pressedColor ripple color resource or pressed color resource
         */
        fun setPressedColor(pressedColor: Int): Builder {
            if (pressedColor > -1) {
                mDrawable.pressedColor = pressedColor
            }
            return this
        }

        /**
         * @param backgroundColor main color resource
         */
        fun setBackgroundColor(backgroundColor: Int): Builder {
            mDrawable.backgroundColor = backgroundColor
            return this
        }

        /**
         * @param borderColor border color resource
         */
        fun setBorderColor(borderColor: Int): Builder {
            mDrawable.borderColor = borderColor
            return this
        }

        /**
         * @param gradientEndColor end color of gradient
         */
        fun setGradientEndColor(gradientEndColor: Int): Builder {
            mDrawable.borderColor = gradientEndColor
            return this
        }

        /**
         * @param pressedColorResId ripple color resource or pressed color resource
         */
        @SuppressLint("ResourceType")

        fun setPressedColorResId(@ColorRes pressedColorResId: Int): Builder {
            if (pressedColorResId > -1) {
                mDrawable.pressedColor = ContextCompat.getColor(context, pressedColorResId)
            }
            return this
        }

        /**
         * @param backgroundColorResId main color resource
         */
        fun setBackgroundColorResId(@ColorRes backgroundColorResId: Int): Builder {
            mDrawable.backgroundColor = ContextCompat.getColor(context, backgroundColorResId)
            return this
        }

        /**
         * @param borderColorResId border color resource
         */
        fun setBorderColorResId(@ColorRes borderColorResId: Int): Builder {
            mDrawable.borderColor = ContextCompat.getColor(context, borderColorResId)
            return this
        }

        /**
         * @param gradientEndColorResId end color resource of gradient
         */
        fun setGradientEndColorResId(@ColorRes gradientEndColorResId: Int): Builder {
            mDrawable.borderColor = ContextCompat.getColor(context, gradientEndColorResId)
            return this
        }
    }

    private fun getDrawable(
        shape: Shape,
        color: Int,
        outerRadii: FloatArray,
        endColor: Int,
        orientation: GradientDrawable.Orientation?
    ): Drawable =
        when (shape) {
            MDrawable.Shape.RECTANGLE -> getRectDrawable(color, outerRadii)
            MDrawable.Shape.OVAL -> getOvalDrawable(color)
            //            case GRADIENT:
            //                return getGradientDrawable(color, outerRadii, endColor, orientation);
            //            else -> getRectDrawable(color, outerRadii)
        }

    //    private Drawable getGradientDrawable(int color, float[] outerRadii, int endColor, GradientDrawable.Orientation orientation) {
    //        val drawable = (GradientDrawable) new GradientDrawable(orientation, new int[]{color, endColor}).mutate();
    //        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    //        drawable.setCornerRadii(outerRadii);
    //
    //        val r = new RoundRectShape(outerRadii, null, null);
    //        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
    //        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    //        shapeDrawable.getPaint().setColor(color);
    //
    //        return new LayerDrawable(new Drawable[]{shapeDrawable, drawable});
    //    }

    private fun getBorderDrawable(
        shape: Shape,
        color: Int,
        outerRadii: FloatArray,
        borderWidth: Float,
        endColor: Int,
        orientation: GradientDrawable.Orientation?
    ): Drawable =
        when (shape) {
            MDrawable.Shape.RECTANGLE -> getRectBorderDrawable(color, outerRadii, borderWidth)
            MDrawable.Shape.OVAL -> getOvalBorderDrawable(color, borderWidth)
            //            case GRADIENT:
            //                return getGradientBorderDrawable(color, outerRadii, borderWidth, endColor, orientation);
        }

    private fun getRectDrawable(color: Int, outerRadii: FloatArray): Drawable {
        //        val drawable = (GradientDrawable) new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{color, color}).mutate();
        //        drawable.setShape(GradientDrawable.RECTANGLE);
        //        drawable.setCornerRadii(outerRadii);

        val r = RoundRectShape(outerRadii, null, null)
        val drawable = ShapeDrawable(r)
        drawable.paint.style = Paint.Style.FILL
        drawable.paint.color = color

        return drawable
    }

    private fun getOvalDrawable(color: Int): Drawable {
        val o = OvalShape()

        val drawable = ShapeDrawable(o)
        drawable.paint.style = Paint.Style.FILL
        drawable.paint.color = color

        return drawable
    }

    private fun getRectBorderDrawable(color: Int, outerRadii: FloatArray, width: Float): Drawable {
        //        val r = new RoundRectShape(outerRadii, null, null);
        //        ShapeDrawable drawable = new ShapeDrawable(r);
        //        drawable.getPaint().setColor(color);
        //        drawable.getPaint().setStyle(Paint.Style.STROKE);
        //        drawable.getPaint().setStrokeWidth(width);

        val drawable =
            GradientDrawable(BOTTOM_TOP, intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT)).mutate() as GradientDrawable
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadii = outerRadii
        drawable.setStroke(width.toInt(), color)

        return drawable
    }

    private fun getOvalBorderDrawable(color: Int, width: Float): Drawable {
        val drawable =
            GradientDrawable(BOTTOM_TOP, intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT)).mutate() as GradientDrawable
        drawable.shape = GradientDrawable.OVAL
        drawable.setStroke(width.toInt(), color)

        return drawable
    }

    /**
     * Creates a new ripple drawable with the specified ripple color and
     * optional content and mask drawables.

     * @param color   The ripple color
     * *
     * @param content The content drawable, may be `null`
     * *
     * @param mask    The mask drawable, may be `null`
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    internal inner class MRippleDrawable(color: ColorStateList, content: Drawable?, mask: Drawable?) :
        RippleDrawable(color, content, mask)

    enum class Shape constructor(val index: Int, val shape: String) {

        RECTANGLE(0, "rectangle"),
        OVAL(1, "oval");

        companion object {

            fun parse(i: Int): Shape {
                return values().firstOrNull { it.index == i }
                    ?: RECTANGLE
            }

            fun parse(shape: String): Shape = when (shape) {
                "rectangle" -> RECTANGLE
                "oval" -> OVAL
                //                case "gradient":
                //                    return GRADIENT;
                else -> RECTANGLE
            }
        }
    }

    enum class Type constructor(val index: Int, val type: String) {

        BACKGROUND(0, "background"),
        BORDER(1, "border");

        companion object {

            fun parse(i: Int): Type {
                return values().firstOrNull { it.index == i }
                    ?: BACKGROUND
            }

            fun parse(type: String): Type = when (type) {
                "background" -> BACKGROUND
                "border" -> BORDER
                else -> BACKGROUND
            }
        }
    }
}