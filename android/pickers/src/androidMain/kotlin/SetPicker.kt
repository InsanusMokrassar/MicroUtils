package dev.inmo.micro_utils.android.pickers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.*

@OptIn(ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun <T> SetPicker(
    current: T,
    dataList: List<T>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    arrowsColor: Color = MaterialTheme.colorScheme.primary,
    dataToString: @Composable (T) -> String = { it.toString() },
    onStateChanged: (T) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val numbersColumnHeight = 8.dp + with(LocalDensity.current) {
        textStyle.lineHeight.toDp()
    }
    val numbersColumnHeightPx = with(LocalDensity.current) { numbersColumnHeight.toPx() }
    val halvedNumbersColumnHeight = numbersColumnHeight / 2
    val halvedNumbersColumnHeightPx = with(LocalDensity.current) { halvedNumbersColumnHeight.toPx() }

    val index = dataList.indexOfFirst { it === current }.takeIf { it > -1 } ?: dataList.indexOf(current)
    val lastIndex = dataList.size - 1

    fun animatedStateValue(offset: Float): Int = index - (offset / halvedNumbersColumnHeightPx).toInt()

    val animatedOffset = remember { Animatable(0f) }.apply {
        val offsetRange = remember(index, lastIndex) {
            val value = index
            val first = -(lastIndex - value) * halvedNumbersColumnHeightPx
            val last = value * halvedNumbersColumnHeightPx
            first..last
        }
        updateBounds(offsetRange.start, offsetRange.endInclusive)
    }
    val indexAnimatedOffset = if (animatedOffset.value > 0) {
        (index - floor(animatedOffset.value / halvedNumbersColumnHeightPx).toInt())
    } else {
        (index - ceil(animatedOffset.value / halvedNumbersColumnHeightPx).toInt())
    }
    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnHeightPx
    val boxOffset = (indexAnimatedOffset * halvedNumbersColumnHeightPx) - coercedAnimatedOffset
    val disabledArrowsColor = arrowsColor.copy(alpha = ContentAlpha.disabled)
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .wrapContentSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { deltaY ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + deltaY)
                    }
                },
                onDragStopped = { velocity ->
                    coroutineScope.launch {
                        val endValue = animatedOffset.fling(
                            initialVelocity = velocity,
                            animationSpec = exponentialDecay(frictionMultiplier = 20f),
                            adjustTarget = { target ->
                                val coercedTarget = target % halvedNumbersColumnHeightPx
                                val coercedAnchors =
                                    listOf(-halvedNumbersColumnHeightPx, 0f, halvedNumbersColumnHeightPx)
                                val coercedPoint = coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base =
                                    halvedNumbersColumnHeightPx * (target / halvedNumbersColumnHeightPx).toInt()
                                coercedPoint + base
                            }
                        ).endState.value

                        onStateChanged(dataList.elementAt(animatedStateValue(endValue)))
                        animatedOffset.snapTo(0f)
                    }
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val spacing = 4.dp

        val upEnabled = index > 0
        IconButton(
            {
                onStateChanged(dataList.elementAt(index - 1))
            },
            enabled = upEnabled
        ) {
            Icon(Icons.Default.KeyboardArrowUp, "", tint = if (upEnabled) arrowsColor else disabledArrowsColor)
        }

        Spacer(modifier = Modifier.height(spacing))
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            ProvideTextStyle(textStyle) {
                dataList.forEachIndexed { i, t ->
                    val alpha = when {
                        i == indexAnimatedOffset - 1 -> coercedAnimatedOffset / halvedNumbersColumnHeightPx
                        i == indexAnimatedOffset -> 1 - (abs(coercedAnimatedOffset) / halvedNumbersColumnHeightPx)
                        i == indexAnimatedOffset + 1 -> -coercedAnimatedOffset / halvedNumbersColumnHeightPx
                        else -> return@forEachIndexed
                    }
                    val offset = when {
                        i == indexAnimatedOffset - 1 && coercedAnimatedOffset > 0 -> coercedAnimatedOffset - halvedNumbersColumnHeightPx
                        i == indexAnimatedOffset -> coercedAnimatedOffset
                        i == indexAnimatedOffset + 1 && coercedAnimatedOffset < 0 -> coercedAnimatedOffset + halvedNumbersColumnHeightPx
                        else -> return@forEachIndexed
                    }
                    Text(
                        text = dataToString(t),
                        modifier = Modifier
                            .alpha(alpha)
                            .offset(y = with(LocalDensity.current) { offset.toDp() })
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(spacing))

        val downEnabled = index < lastIndex
        IconButton(
            {
                onStateChanged(dataList.elementAt(index + 1))
            },
            enabled = downEnabled
        ) {
            Icon(Icons.Default.KeyboardArrowDown, "", tint = if (downEnabled) arrowsColor else disabledArrowsColor)
        }
    }
}
