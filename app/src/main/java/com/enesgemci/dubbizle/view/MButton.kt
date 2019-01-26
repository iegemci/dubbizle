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

package com.enesgemci.dubbizle.view

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.util.UIUtil
import io.kambo.core.util.background.MDrawable
import java.util.*

/**
 * Created by enesgemci on 10/09/15.
 */
class MButton : AppCompatButton {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val array = getContext().obtainStyledAttributes(attrs, R.styleable.MButton)

            val rad = resources.getDimensionPixelOffset(R.dimen.common_radius)

            try {
                val borderWidth = array.getDimensionPixelSize(R.styleable.MButton_border_width, 0)
                val borderColor = array.getResourceId(R.styleable.MButton_border_color, -1)
                val backgroundColor = array.getResourceId(R.styleable.MButton_background_color, -1)
                val pressedColor = array.getResourceId(R.styleable.MButton_pressed_color, -1)
                val radius = array.getDimensionPixelOffset(R.styleable.MButton_radius, -1)
                var radiusTopLeft = array.getDimensionPixelOffset(R.styleable.MButton_radius_top_left, rad)
                var radiusTopRight = array.getDimensionPixelOffset(R.styleable.MButton_radius_top_right, rad)
                var radiusBottomRight = array.getDimensionPixelOffset(R.styleable.MButton_radius_bottom_right, rad)
                var radiusBottomLeft = array.getDimensionPixelOffset(R.styleable.MButton_radius_bottom_left, rad)
                val shape =
                    MDrawable.Shape.parse(array.getInteger(R.styleable.MButton_shape, MDrawable.Shape.RECTANGLE.index))
                val types = array.getString(R.styleable.MButton_background_type)

                val typeList = ArrayList<MDrawable.Type>()

                if (!isInEditMode) {
                    if (!TextUtils.isEmpty(types)) {
                        if (types!!.contains(",")) {
                            val tt = types
                                .replace(" ", "")
                                .trim { it <= ' ' }
                                .split(",".toRegex())
                                .dropLastWhile { it.isEmpty() }
                                .toTypedArray()

                            tt
                                .map { MDrawable.Type.parse(it) }
                                .filterNot { typeList.contains(it) }
                                .forEach { typeList.add(it) }
                        } else {
                            var type: MDrawable.Type?

                            type = try {
                                MDrawable.Type.parse(Integer.parseInt(types))
                            } catch (e: Exception) {
                                MDrawable.Type.parse(types)
                            }

                            if (!typeList.contains(type)) {
                                typeList.add(type)
                            }
                        }
                    }
                }

                if (!typeList.contains(MDrawable.Type.BACKGROUND)) {
                    typeList.add(MDrawable.Type.BACKGROUND)
                }

                if (radius > -1) {
                    radiusTopLeft = radius
                    radiusTopRight = radius
                    radiusBottomRight = radius
                    radiusBottomLeft = radius
                }

                val backgroundResId = attrs.getAttributeResourceValue(UIUtil.XML_NAMESPACE_ANDROID, "background", -1)

                if (backgroundResId == -1) {
                    var builder: MDrawable.Builder = MDrawable.Builder(context)
                        .setBackgroundColorResId(
                            if (backgroundColor != -1)
                                backgroundColor
                            else
                                R.color.blue
                        )
                        .setPressedColorResId(
                            when {
                                pressedColor != -1 -> pressedColor
                                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP -> R.color.blue_pressed
                                else -> R.color.blue_pressed
                            }
                        )

                    if (borderColor != -1 && borderWidth > 0) {
                        if (!typeList.contains(MDrawable.Type.BORDER)) {
                            typeList.add(MDrawable.Type.BORDER)
                            builder = builder.setBorderColorResId(borderColor)
                        }
                    }

                    background = builder.setTopLeftRadius(radiusTopLeft.toFloat())
                        .setTopRightRadius(radiusTopRight.toFloat())
                        .setBottomRightRadius(radiusBottomRight.toFloat())
                        .setBottomLeftRadius(radiusBottomLeft.toFloat())
                        .setBorderWidth(borderWidth.toFloat())
                        .addType(typeList)
                        .setShape(shape)
                        .build()
                } else {
                    throw UnsupportedOperationException("Do not use drawable resource, use dynamic drawable instead.")
                }

            } finally {
                array.recycle()
            }
        }
    }
}