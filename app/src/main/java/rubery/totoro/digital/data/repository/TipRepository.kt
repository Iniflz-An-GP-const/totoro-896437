package rubery.totoro.digital.data.repository

import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.data.model.TipText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TipRepository {
    private val tips = listOf(
        TipModel(
            id = 1,
            texts = mapOf(
                "en" to TipText(
                    title = "Stay Hydrated",
                    message = "Drink water before, during, and after training to keep your muscles performing.",
                ),
            ),
        ),
        TipModel(
            id = 2,
            texts = mapOf(
                "en" to TipText(
                    title = "Warm Up First",
                    message = "Five minutes of light movement prepares your joints and lowers injury risk.",
                ),
            ),
        ),
        TipModel(
            id = 3,
            texts = mapOf(
                "en" to TipText(
                    title = "Sleep Well",
                    message = "Seven to nine hours of sleep is when your body actually builds strength.",
                ),
            ),
        ),
        TipModel(
            id = 4,
            texts = mapOf(
                "en" to TipText(
                    title = "Progress Gradually",
                    message = "Add a little weight or time each week instead of jumping ahead too fast.",
                ),
            ),
        ),
        TipModel(
            id = 5,
            texts = mapOf(
                "en" to TipText(
                    title = "Rest Days Matter",
                    message = "Recovery days let your muscles repair and make the next workout stronger.",
                ),
            ),
        ),
        TipModel(
            id = 6,
            texts = mapOf(
                "en" to TipText(
                    title = "Fuel Your Body",
                    message = "Eat protein and carbs within two hours after training to speed up recovery.",
                ),
            ),
        ),
    )

    fun observeAll(): Flow<List<TipModel>> {
        return flowOf(tips)
    }
}
