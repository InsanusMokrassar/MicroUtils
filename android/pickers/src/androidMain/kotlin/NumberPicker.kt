package dev.inmo.micro_utils.android.pickers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import dev.inmo.micro_utils.android.smalltextfield.SmallTextField
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private inline fun PointerInputScope.checkContains(offset: Offset): Boolean {
    return ((size.center.x - offset.x).absoluteValue < size.width / 2) && ((size.center.y - offset.y).absoluteValue < size.height / 2)
}

// src: https://gist.github.com/vganin/a9a84653a9f48a2d669910fbd48e32d5

@OptIn(ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NumberPicker(
    number: Int,
    modifier: Modifier = Modifier,
    range: IntRange? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    arrowsColor: Color = MaterialTheme.colorScheme.primary,
    allowUseManualInput: Boolean = true,
    onStateChanged: (Int) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val numbersColumnHeight = 36.dp
    val halvedNumbersColumnHeight = numbersColumnHeight / 2
    val halvedNumbersColumnHeightPx = with(LocalDensity.current) { halvedNumbersColumnHeight.toPx() }

    fun animatedStateValue(offset: Float): Int = number - (offset / halvedNumbersColumnHeightPx).toInt()

    val animatedOffset = remember { Animatable(0f) }.apply {
        if (range != null) {
            val offsetRange = remember(number, range) {
                val value = number
                val first = -(range.last - value) * halvedNumbersColumnHeightPx
                val last = -(range.first - value) * halvedNumbersColumnHeightPx
                first..last
            }
            updateBounds(offsetRange.start, offsetRange.endInclusive)
        }
    }
    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnHeightPx
    val animatedStateValue = animatedStateValue(animatedOffset.value)
    val disabledArrowsColor = arrowsColor.copy(alpha = 0f)

    val inputFieldShown = if (allowUseManualInput) {
        remember { mutableStateOf(false) }
    } else {
        null
    }

    Column(
        modifier = modifier
            .wrapContentSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { deltaY ->
                    if (inputFieldShown ?.value != true) {
                        coroutineScope.launch {
                            animatedOffset.snapTo(animatedOffset.value + deltaY)
                        }
                    }
                },
                onDragStopped = { velocity ->
                    if (inputFieldShown ?.value != true) {
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

                            onStateChanged(animatedStateValue(endValue))
                            animatedOffset.snapTo(0f)
                        }
                    }
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val spacing = 4.dp

        val upEnabled = range == null || range.first < number
        IconButton(
            {
                onStateChanged(number - 1)
                inputFieldShown ?.value = false
            },
            enabled = upEnabled
        ) {
            Icon(Icons.Default.KeyboardArrowUp, "", tint = if (upEnabled) arrowsColor else disabledArrowsColor)
        }

        Spacer(modifier = Modifier.height(spacing))
        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = coercedAnimatedOffset.roundToInt()) },
            contentAlignment = Alignment.Center
        ) {
            val baseLabelModifier = Modifier.align(Alignment.Center)
            ProvideTextStyle(textStyle) {
                Text(
                    text = (animatedStateValue - 1).toString(),
                    modifier = baseLabelModifier
                        .offset(y = -halvedNumbersColumnHeight)
                        .alpha(coercedAnimatedOffset / halvedNumbersColumnHeightPx)
                )

                if (inputFieldShown ?.value == true) {
                    val currentValue = remember { mutableStateOf(number.toString()) }

                    val focusRequester = remember { FocusRequester() }
                    SmallTextField(
                        currentValue.value,
                        {
                            val asDigit = it.toIntOrNull()
                            when {
                                (asDigit == null && it.isEmpty()) -> currentValue.value = (range ?.first ?: 0).toString()
                                (asDigit != null && (range == null || asDigit in range)) -> currentValue.value = it
                                else -> { /* do nothing */ }
                            }
                        },
                        baseLabelModifier.focusRequester(focusRequester).width(IntrinsicSize.Min).pointerInput(number) {
                            detectTapGestures {
                                if (!checkContains(it)) {
                                    currentValue.value.toIntOrNull() ?.let(onStateChanged)
                                    inputFieldShown.value = false
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions {
                            currentValue.value.toIntOrNull() ?.let(onStateChanged)
                            inputFieldShown.value = false
                        },
                        singleLine = true,
                        textStyle = textStyle
                    )
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                } else {
                    Text(
                        text = animatedStateValue.toString(),
                        modifier = baseLabelModifier
                            .alpha(1 - abs(coercedAnimatedOffset) / halvedNumbersColumnHeightPx)
                            .clickable {
                                if (inputFieldShown ?.value == false) {
                                    inputFieldShown.value = true
                                }
                            }
                    )
                }
                Text(
                    text = (animatedStateValue + 1).toString(),
                    modifier = baseLabelModifier
                        .offset(y = halvedNumbersColumnHeight)
                        .alpha(-coercedAnimatedOffset / halvedNumbersColumnHeightPx)
                )
            }
        }


        Spacer(modifier = Modifier.height(spacing))

        val downEnabled = range == null || range.last > number
        IconButton(
            {
                onStateChanged(number + 1)
                inputFieldShown ?.value = false
            },
            enabled = downEnabled
        ) {
            Icon(Icons.Default.KeyboardArrowDown, "", tint = if (downEnabled) arrowsColor else disabledArrowsColor)
        }
    }
}

