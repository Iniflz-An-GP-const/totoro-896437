package rubery.totoro.digital.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val RROIL_ONBOARDING_PREFS_NAME = "rroil_sport_tracker_onboarding_prefs"

val Context.onboardingDataStore by preferencesDataStore(name = RROIL_ONBOARDING_PREFS_NAME)

class OnboardingDataStoreManager(
    private val context: Context
) {
    val onboardedStateFlow: Flow<Boolean?> = context.onboardingDataStore.data.map { prefs ->
        prefs[ONBOARDED_STATE_KEY]
    }

    suspend fun setOnboardedState(state: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[ONBOARDED_STATE_KEY] = state
        }
    }

    companion object {
        private val ONBOARDED_STATE_KEY = booleanPreferencesKey("onboardedState")
    }
}