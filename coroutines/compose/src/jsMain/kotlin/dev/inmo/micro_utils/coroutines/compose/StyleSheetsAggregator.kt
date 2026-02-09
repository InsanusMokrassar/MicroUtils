package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import dev.inmo.micro_utils.coroutines.MutableRedeliverStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import org.jetbrains.compose.web.css.CSSRulesHolder
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet

/**
 * Aggregator of Compose CSS StyleSheet. Allowing to add [StyleSheet] in it and draw it in one place without requiring
 * to add `Style(stylesheet)` on every compose function call
 */
object StyleSheetsAggregator {
    private val _stylesFlow = MutableRedeliverStateFlow<Set<CSSRulesHolder>>(emptySet())
    val stylesFlow: StateFlow<Set<CSSRulesHolder>> = _stylesFlow.asStateFlow()

    @Composable
    fun draw() {
        _stylesFlow.debounce(13L).collectAsState(emptySet()).value.forEach {
            Style(it)
        }
    }

    /**
     * Adding [styleSheet] into the [Set] of included stylesheets. If you called [enableStyleSheetsAggregator],
     * new styles will be enabled in the document
     */
    fun addStyleSheet(styleSheet: CSSRulesHolder) {
        _stylesFlow.value += styleSheet
    }

    /**
     * Removed [styleSheet] into the [Set] of included stylesheets
     */
    fun removeStyleSheet(styleSheet: CSSRulesHolder) {
        _stylesFlow.value -= styleSheet
    }
}

/**
 * Drawing [StyleSheetsAggregator] in place. You may pass [Set] of [CSSRulesHolder]/[StyleSheet]s as preset of styles
 */
@Composable
fun enableStyleSheetsAggregator(
    stylesPreset: Set<CSSRulesHolder> = emptySet(),
) {
    remember {
        stylesPreset.forEach {
            StyleSheetsAggregator.addStyleSheet(it)
        }
    }
    StyleSheetsAggregator.draw()
}

/**
 * Will include [this] [CSSRulesHolder]/[StyleSheet] in the [StyleSheetsAggregator] using its
 * [StyleSheetsAggregator.addStyleSheet]
 */
fun CSSRulesHolder.includeInStyleSheetsAggregator() {
    StyleSheetsAggregator.addStyleSheet(this)
}
