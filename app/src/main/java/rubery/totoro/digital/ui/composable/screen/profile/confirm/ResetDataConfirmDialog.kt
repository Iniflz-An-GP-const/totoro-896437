package rubery.totoro.digital.ui.composable.screen.profile.confirm

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.theme.LocalDimens

private const val ArmingMs = 2_500
private val ConfirmHeight = 40.dp
private val IconSize = 36.dp

@Composable
fun ResetDataConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val dimens = LocalDimens.current
    val shape = RoundedCornerShape(dimens.controlCornerRadius)
    val progress = remember { Animatable(0f) }
    val isArmed = progress.value >= 1f

    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(durationMillis = ArmingMs, easing = LinearEasing))
    }

    val error = MaterialTheme.colorScheme.error
    val labelColor by animateColorAsState(
        targetValue = if (isArmed) MaterialTheme.colorScheme.onError else error,
        label = "confirmLabel",
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(dimens.cardCornerRadius),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(dimens.cardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteForever,
                    contentDescription = null,
                    tint = error,
                    modifier = Modifier.size(IconSize),
                )

                Spacer(modifier = Modifier.height(dimens.itemSpacing))

                Text(
                    text = stringResource(R.string.profile_screen_reset_data_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(dimens.tightSpacing))

                Text(
                    text = stringResource(R.string.profile_screen_reset_data_dialog_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(dimens.sectionGap))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(ConfirmHeight),
                        shape = shape,
                    ) {
                        Text(stringResource(R.string.exercise_dialog_cancel))
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(ConfirmHeight)
                            .clip(shape)
                            .background(error.copy(alpha = 0.16f))
                            .drawBehind {
                                drawRect(
                                    color = error,
                                    size = Size(size.width * progress.value, size.height)
                                )
                            }
                            .clickable(enabled = isArmed, onClick = onConfirm),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.profile_screen_reset_data_dialog_confirm),
                            style = MaterialTheme.typography.labelLarge,
                            color = labelColor,
                        )
                    }
                }
            }
        }
    }
}
