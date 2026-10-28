package rubery.totoro.digital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import rubery.totoro.digital.ui.composable.approot.AppRoot
import rubery.totoro.digital.ui.theme.RROILAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RROILAppTheme {
                AppRoot()
            }
        }
    }
}