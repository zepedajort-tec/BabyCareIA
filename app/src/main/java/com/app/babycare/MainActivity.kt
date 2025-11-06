package com.app.babycare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.app.babycare.ui.navigation.AppNavGraph
import com.app.babycare.ui.theme.BabyCareIATheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabyCareIATheme {
                val navController = rememberNavController()
                // Optional: side effects (status bar) can go here
                SideEffect { /* system UI changes */ }

                AppNavGraph(navController = navController)
            }
        }
    }
}
