package cl.huertohogar.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.huertohogar.app.ui.theme.HuertoHogarAppTheme
import cl.huertohogar.app.navigation.NavManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuertoHogarAppTheme {
                NavManager()
            }
        }
    }
}