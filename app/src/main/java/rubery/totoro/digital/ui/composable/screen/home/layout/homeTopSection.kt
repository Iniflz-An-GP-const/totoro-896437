package rubery.totoro.digital.ui.composable.screen.home.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rubery.totoro.digital.R
import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.ui.composable.screen.home.goalcard.WeeklyGoalCard
import rubery.totoro.digital.ui.composable.screen.home.greeting.HomeGreeting
import rubery.totoro.digital.ui.composable.screen.home.tips.TipCard
import rubery.totoro.digital.ui.theme.LocalDimens

fun LazyListScope.homeTopSection(
    tips: List<TipModel>,
    weeklyGoal: Int,
    weeklyProgress: Int,
) {
    item {
        val dimens = LocalDimens.current
        HomeGreeting(
            title = stringResource(R.string.home_greeting_title),
            text = stringResource(R.string.home_greeting_text),
            modifier = Modifier
                .padding(horizontal = dimens.screenPadding),
        )
    }

    if (tips.isNotEmpty()) {
        item {
            val dimens = LocalDimens.current
            TipCard(
                tips = tips,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.screenPadding),
            )
        }
    }

    item {
        val dimens = LocalDimens.current
        WeeklyGoalCard(
            weeklyGoal = weeklyGoal,
            completed = weeklyProgress,
            modifier = Modifier
                .padding(horizontal = dimens.screenPadding),
        )
    }
}
