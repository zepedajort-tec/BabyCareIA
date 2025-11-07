package com.app.babycare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.babycare.ui.compose.HomeScreen
import com.app.babycare.ui.compose.LoginScreenWithViewModel
import com.app.babycare.ui.compose.RegisterScreen
import com.app.babycare.ui.compose.SplashScreen
import com.app.babycare.ui.viewmodel.LoginViewModel
import com.app.babycare.ui.viewmodel.RegisterViewModel
import com.app.babycare.ui.viewmodel.SplashViewModel

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String = Destinations.SPLASH) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH) {
            // Use Hilt to provide VM; listen to events to navigate
            val splashVm: SplashViewModel = hiltViewModel()
            SplashScreen(
                viewModel = splashVm,
                onNavigateToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.LOGIN) {
            val loginVm: LoginViewModel = hiltViewModel()
            LoginScreenWithViewModel(
                viewModel = loginVm,
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }

        composable(Destinations.REGISTER) {
            val registerVm: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = registerVm,
                onNavigateToLogin = {
                    // Navigate back to login and clear register from backstack
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.HOME) {
            HomeScreen(
                onSignOut = {
                    // Example: navigate to login and clear backstack
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(0) { inclusive = true } // clear back stack (or specify routes)
                    }
                }
            )
        }
    }
}
