package rubery.totoro.digital.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val RROIL_PROFILE_PREFS_NAME = "rroil_sport_tracker_profile_prefs"

const val DEFAULT_WEEKLY_GOAL = 3

val Context.profileDataStore by preferencesDataStore(name = RROIL_PROFILE_PREFS_NAME)

class ProfileDataStoreManager(
    private val context: Context
) {
    val weeklyGoalFlow: Flow<Int> = context.profileDataStore.data.map { prefs ->
        prefs[WEEKLY_GOAL_KEY] ?: DEFAULT_WEEKLY_GOAL
    }

    val heightCmFlow: Flow<Int?> = context.profileDataStore.data.map { prefs ->
        prefs[HEIGHT_CM_KEY]
    }

    val weightKgFlow: Flow<Int?> = context.profileDataStore.data.map { prefs ->
        prefs[WEIGHT_KG_KEY]
    }

    suspend fun setWeeklyGoal(value: Int?) {
        context.profileDataStore.edit { prefs ->
            if (value == null) prefs.remove(WEEKLY_GOAL_KEY) else prefs[WEEKLY_GOAL_KEY] = value
        }
    }

    suspend fun setHeightCm(value: Int?) {
        context.profileDataStore.edit { prefs ->
            if (value == null) prefs.remove(HEIGHT_CM_KEY) else prefs[HEIGHT_CM_KEY] = value
        }
    }

    suspend fun setWeightKg(value: Int?) {
        context.profileDataStore.edit { prefs ->
            if (value == null) prefs.remove(WEIGHT_KG_KEY) else prefs[WEIGHT_KG_KEY] = value
        }
    }

    companion object {
        private val WEEKLY_GOAL_KEY = intPreferencesKey("weeklyGoal")
        private val HEIGHT_CM_KEY = intPreferencesKey("heightCm")
        private val WEIGHT_KG_KEY = intPreferencesKey("weightKg")
    }
}
