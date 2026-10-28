package rubery.totoro.digital.ui.composable.screen.onboarding.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import rubery.totoro.digital.ui.composable.screen.onboarding.content.OnboardingContentMaxWidth
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.LocalOnboardingPageActive
import rubery.totoro.digital.ui.composable.screen.onboarding.motion.iconMotion
import rubery.totoro.digital.ui.composable.screen.onboarding.textmotion.textMotion
import rubery.totoro.digital.ui.theme.LocalDimens
import kotlin.math.PI
import kotlin.math.sin

private data class BubbleSpec(
    val biasX: Float,
    val biasY: Float,
    val size: Dp,
    val driftX: Dp,
    val driftY: Dp,
    val periodMs: Int,
)

// Positions are fixed, so the bubbles never run into each other or into the text.
private val IconBubbles = listOf(
    BubbleSpec(
        biasX = -0.45f,
        biasY = -0.35f,
        size = 92.dp,
        driftX = 10.dp,
        driftY = 14.dp,
        periodMs = 3600
    ),
    BubbleSpec(
        biasX = 0.55f,
        biasY = 0.05f,
        size = 68.dp,
        driftX = 12.dp,
        driftY = 10.dp,
        periodMs = 4300
    ),
    BubbleSpec(
        biasX = -0.2f,
        biasY = 0.7f,
        size = 52.dp,
        driftX = 8.dp,
        driftY = 12.dp,
        periodMs = 3100
    ),
)

// Small empty bubbles that only fill the space around the icon ones.
private val DecorBubbles = listOf(
    BubbleSpec(
        biasX = 0.7f,
        biasY = -0.8f,
        size = 22.dp,
        driftX = 6.dp,
        driftY = 8.dp,
        periodMs = 3300
    ),
    BubbleSpec(
        biasX = -0.85f,
        biasY = 0.45f,
        size = 16.dp,
        driftX = 5.dp,
        driftY = 7.dp,
        periodMs = 2900
    ),
    BubbleSpec(
        biasX = 0.25f,
        biasY = -0.6f,
        size = 12.dp,
        driftX = 4.dp,
        driftY = 6.dp,
        periodMs = 3800
    ),
)

private const val ArtWeight = 1f
private const val TextWeight = 0.7f
private const val DecorAlpha = 0.5f
private const val IconBackdropShare = 0.5f

@Composable
fun OnboardingPageContent(
    title: String,
    description: String,
    icons: List<ImageVector>,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier
            .widthIn(max = OnboardingContentMaxWidth)
            .fillMaxSize()
            .padding(horizontal = dimens.cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(ArtWeight),
        ) {
            DecorBubbles.forEach { spec ->
                Bubble(
                    spec = spec,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = DecorAlpha * 0.4f)),
                )
            }

            icons.take(IconBubbles.size).forEachIndexed { index, icon ->
                val spec = IconBubbles[index]
                val isLead = index == 0

                Bubble(
                    spec = spec,
                    modifier = Modifier
                        .iconMotion(index)
                        .clip(CircleShape)
                        .background(
                            if (isLead) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primaryContainer,
                        )
                        .border(
                            width = dimens.imageBorderWidth,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = DecorAlpha),
                            shape = CircleShape,
                        ),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isLead) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        },
                        modifier = Modifier.size(spec.size * IconBackdropShare),
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(TextWeight)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier.textMotion(0),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(dimens.tightSpacing))

            Text(
                text = description,
                modifier = Modifier.textMotion(1),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun BoxScope.Bubble(
    spec: BubbleSpec,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val density = LocalDensity.current
    val active = LocalOnboardingPageActive.current
    val x = driftPhase(cycleMs = spec.periodMs * 2, active = active)
    val y = driftPhase(cycleMs = spec.periodMs * 5 / 2, active = active)

    Box(
        modifier = Modifier
            .align(BiasAlignment(spec.biasX, spec.biasY))
            .graphicsLayer {
                translationX = with(density) { spec.driftX.toPx() } * x()
                translationY = with(density) { spec.driftY.toPx() } * y()
            }
            .size(spec.size)
            .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun driftPhase(cycleMs: Int, active: Boolean): () -> Float {
    if (!active) return { 0f }

    val phase = rememberInfiniteTransition(label = "bubbleDrift").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = cycleMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "bubbleDriftPhase",
    )
    return { sin(2f * PI.toFloat() * phase.value) }
}
