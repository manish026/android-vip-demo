package com.marketpulse.sniper.myapplication.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marketpulse.sniper.myapplication.appModule
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCase
import com.marketpulse.sniper.myapplication.presenter.LoginViewModel
import com.marketpulse.sniper.myapplication.ui.theme.MyApplicationTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            modules(appModule)
        }
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = Route.LoginRoute) {

                        composable(Route.LoginRoute) {
                            val vieModel: LoginViewModel = koinViewModel()
                            val loginUseCase: LoginUseCase = vieModel.scope.get()
                            LoginScreen(navController = navController, loginUseCase = loginUseCase)
                        }

                        composable(Route.DashboardRoute) {
                            DashboardScreen()
                        }

                        composable(Route.ErrorRoute.key) {
                            val message = it.arguments?.getString(Route.ErrorRoute.message) ?: ""
                            ErrorScreen(error = message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: Route,
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    builder: NavGraphBuilder.() -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination.key,
        modifier = modifier,
        builder = builder,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition }
    )
}

fun NavGraphBuilder.composable(
    route: Route,
    enterTransition: EnterTransition = EnterTransition.None,
    exitTransition: ExitTransition = ExitTransition.None,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route.key,
        content = content,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition }
    )
}

fun NavHostController.navigate(destination: Route) {
    this.navigate(destination.key) {
        if (destination.clearBackStack) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }
}

