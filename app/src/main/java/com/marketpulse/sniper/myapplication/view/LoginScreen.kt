package com.marketpulse.sniper.myapplication.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.marketpulse.sniper.myapplication.appModule
import com.marketpulse.sniper.myapplication.domain.login.LoginAction
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCase
import com.marketpulse.sniper.myapplication.presenter.LoginEffect
import com.marketpulse.sniper.myapplication.presenter.LoginState
import com.marketpulse.sniper.myapplication.ui.theme.MyApplicationTheme
import com.marketpulse.sniper.myapplication.ui.theme.PaddingLarge
import com.marketpulse.sniper.myapplication.ui.theme.PaddingSmall
import org.koin.compose.KoinApplication

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUseCase: LoginUseCase,
    navController: NavHostController
) {
    val presenter = loginUseCase.presenter
    val state by presenter.state.collectAsState()

    LaunchedEffect(Unit) {
        presenter.effect.collect {
            when(it) {
                LoginEffect.ShowDashboard -> navController.navigate(Route.DashboardRoute)
                is LoginEffect.ShowErrorScreen -> navController.navigate(Route.ErrorRoute(it.error))
            }
        }
    }


    LoginContent(modifier, state) {
        loginUseCase.perform(it)
    }

}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp), Arrangement.Center
    ) {
        UsernameTextField(state, onAction)
        Spacer(modifier = Modifier.height(PaddingSmall))
        PasswordTextField(state, onAction)
        Spacer(modifier = Modifier.height(PaddingLarge))
        LoginButton(onAction, state)
    }
}

@Composable
private fun UsernameTextField(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    LoginTextField(state.username, state.userNameError, hint = "Username") {
        onAction(LoginAction.UsernameTyped(it))
    }
}

@Composable
private fun PasswordTextField(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    LoginTextField(
        state.password,
        state.passwordError,
        hint = "Password",
        visualTransformation = PasswordVisualTransformation('*')
    ) {
        onAction(LoginAction.PasswordTyped(it))
    }
}

@Composable
private fun LoginTextField(
    value: String,
    isError: Boolean,
    hint: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (String) -> Unit
) {
    TextField(
        value = value,
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        placeholder = {
            Text(text = hint)
        },
        onValueChange = {
            onValueChanged(it)
        })
}

@Composable
private fun LoginButton(
    onAction: (LoginAction) -> Unit,
    state: LoginState
) {
    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
        Button(onClick = {
            onAction(LoginAction.LoginClicked(state.username, state.password))
        }) {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MyApplicationTheme {
        KoinApplication(application = {
            // your preview config here
            modules(appModule)
        }) {
            LoginContent(state = LoginState.empty()) {

            }
        }
    }
}