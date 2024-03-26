package com.marketpulse.sniper.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marketpulse.sniper.myapplication.domain.login.LoginAction
import com.marketpulse.sniper.myapplication.domain.login.UseCase
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter
import com.marketpulse.sniper.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = Route.LoginRoute) {

                        composable(Route.LoginRoute) {
                            val loginUseCase: UseCase<LoginPresenter, LoginAction> = koinUsecase(
                                key = Route.LoginRoute,
                                owner = it
                            )
                            Log.e("TAG", "usecase " + loginUseCase.hashCode())
                            LoginScreen(navController = navController, loginUseCase = loginUseCase)
                        }

                        composable(Route.DashboardRoute) {
                            DashboardScreen()
                        }

                        composable(Route.ErrorRoute.key) {
                            val scope = it.getKoinScope(id= "Error")
                            val loginUseCase: UseCase<LoginPresenter, LoginAction> = remember {
                                scope.get()
                            }
                            Log.e("TAG", "usecase " + loginUseCase.hashCode())
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

val navigationScope = CoroutineScope(Dispatchers.Main)

@Composable
fun NavBackStackEntry.getKoinScope(route: Route): Scope {
    return getKoinScope(id = route.key)
}

@Composable
fun NavBackStackEntry.getKoinScope(id: String): Scope {
    val koin = getKoin()
    val scope = remember {
        koin.getOrCreateScope(id, named(id))
    }

    LaunchedEffect(Unit) {
        navigationScope.launch {
            lifecycle.currentStateFlow.collectLatest {
                when(it) {
                    Lifecycle.State.DESTROYED -> {
                        scope.close()
                        cancel()
                    }
                    else -> {}
                }
            }
        }
    }
    return scope
}

@Composable
inline fun <reified T> koinUsecase(key: Route, owner: NavBackStackEntry): T {
    val scope = owner.getKoinScope(route = key)
    return remember {
        scope.get()
    }
}

