package com.example.shoppinglist2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist2app.navigation.AppNavigation
import com.example.shoppinglist2app.ui.theme.ShoppingList2AppTheme
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
