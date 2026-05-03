package com.example.shoppinglist2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.shoppinglist2app.navigation.AppNavigation
import com.example.shoppinglist2app.ui.theme.ShoppingList2AppTheme
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { provider ->
            provider.view.animate()
                .alpha(0f)
                .scaleX(0.96f)
                .scaleY(0.96f)
                .setDuration(220L)
                .withEndAction { provider.remove() }
                .start()
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingList2AppTheme {
                val viewModel: ShoppingViewModel = viewModel()
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
