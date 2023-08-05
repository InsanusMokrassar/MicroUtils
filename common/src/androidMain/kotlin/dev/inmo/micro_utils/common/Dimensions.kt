@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.common

import android.content.res.Resources

inline fun Resources.getSp(
    resId: Int
) = getDimension(resId) / displayMetrics.scaledDensity

inline fun Resources.getDp(
    resId: Int
) = getDimension(resId) * displayMetrics.density
