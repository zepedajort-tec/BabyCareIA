package com.app.babycare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.babycare.ui.compose.AddBabyScreen
import com.app.babycare.ui.compose.HomeScreen
import com.app.babycare.ui.compose.LoginScreenWithViewModel
import com.app.babycare.ui.compose.ProfileScreen
import com.app.babycare.ui.compose.RegisterScreen
import com.app.babycare.ui.compose.SplashScreen
import com.app.babycare.ui.viewmodel.AddBabyEvent
import com.app.babycare.ui.viewmodel.AddBabyViewModel
import com.app.babycare.ui.viewmodel.HomeViewModel
import com.app.babycare.ui.viewmodel.LoginEvent
import com.app.babycare.ui.viewmodel.LoginViewModel
import com.app.babycare.ui.viewmodel.ProfileViewModel
import com.app.babycare.ui.viewmodel.RegisterViewModel
import com.app.babycare.ui.viewmodel.SplashViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String = Destinations.SPLASH) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH) {
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen(
                viewModel = viewModel,
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
            val viewModel: LoginViewModel = hiltViewModel()
            val loginUiState = viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.events.collectLatest { event ->
                    when (event) {
                        is LoginEvent.NavigateToHome -> {
                            navController.navigate(Destinations.HOME) {
                                popUpTo(Destinations.LOGIN) { inclusive = true }
                            }
                        }
                        is LoginEvent.NavigateToRegister -> {
                            navController.navigate(Destinations.REGISTER)
                        }
                        is LoginEvent.NavigateToRegisterBaby -> {
                            navController.navigate(Destinations.ADD_BABY) {
                                popUpTo(Destinations.LOGIN) { inclusive = true }
                            }
                        }
                        else -> {}
                    }
                }
            }

            LoginScreenWithViewModel(
                loginUiState = loginUiState.value,
                onLogin = viewModel::login,
                onNavigateToRegister = viewModel::navigateToRegister,
                onClearError = viewModel::clearError
            )
        }

        composable(Destinations.REGISTER) {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                },
                onNavigateToAddBaby = {
                    navController.navigate(Destinations.ADD_BABY) {
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.ADD_BABY) {
            val viewModel: AddBabyViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val currentParentId = viewModel.currentParentId.collectAsStateWithLifecycle()

            // Observar eventos para navegación
            LaunchedEffect(Unit) {
                viewModel.events.collectLatest { event ->
                    when (event) {
                        is AddBabyEvent.NavigateToHome -> {
                            navController.navigate(Destinations.HOME) {
                                popUpTo(Destinations.ADD_BABY) { inclusive = true }
                            }
                        }
                        is AddBabyEvent.ShowMessage -> {
                            // Los mensajes se manejan en el UI a través del estado
                        }
                    }
                }
            }

            AddBabyScreen(
                uiState = uiState.value,
                onSaveBaby = { name, ageMonths, sex, weight, height ->
                    // Usamos el parentId guardado si existe; si no, la ViewModel lo validará y mostrará mensaje
                    viewModel.saveBaby(
                        parentId = currentParentId.value,
                        name = name,
                        ageMonths = ageMonths,
                        sex = sex,
                        weight = weight,
                        height = height
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onClearError = viewModel::clearError
            )
        }

        composable(Destinations.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                onProfileClick = {
                    navController.navigate(Destinations.PROFILE)
                },
                onLogoutClick = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onChildFriendlyClick = {},
                onTipClick= {},
                onViewAllTips = {},
                onNewRecordClick= {},
                onViewRecordsClick= {},
                onRecordClick = {}
            )
        }
        composable(Destinations.PROFILE) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            ProfileScreen(
                uiState = uiState.value,
                onBackClick = {
                    navController.popBackStack()
                }

            )

        }
    }
}