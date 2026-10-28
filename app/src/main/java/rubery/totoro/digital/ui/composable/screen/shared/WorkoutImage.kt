package rubery.totoro.digital.ui.composable.screen.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import rubery.totoro.digital.R
import rubery.totoro.digital.ui.composable.screen.shared.loadingplaceholder.LoadingPlaceholder
import coil3.compose.AsyncImage

@Composable
fun WorkoutImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(R.string.workout_image_description),
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember(imageUrl) { mutableStateOf(true) }

    Box(modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest)) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
            fallback = painterResource(R.drawable.ic_launcher_background),
            contentScale = contentScale,
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = { isLoading = true },
        )

        if (isLoading) {
            LoadingPlaceholder(modifier = Modifier.matchParentSize())
        }
    }
}
