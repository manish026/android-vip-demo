package com.marketpulse.sniper.myapplication.presenter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: LoginPresenter() {

    override val state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.empty())
    override val effect: MutableSharedFlow<LoginEffect> = MutableSharedFlow(replay = 0)

    override fun setInvalidPassword() {
        state.value = state.value.copy(passwordError = true)
    }

    override fun setPassword(password: String) {
        state.value = state.value.copy(password = password, passwordError = false)
    }

    override fun setInvalidUsername() {
        state.value = state.value.copy(userNameError = true)
    }

    override fun setUsername(username: String) {
        state.value = state.value.copy(username = username, userNameError = false)
    }

    override fun loginSuccess() {
        viewModelScope.launch {
            effect.emit(LoginEffect.ShowDashboard)
        }
    }

    override fun loginFailure(error: Throwable) {
        viewModelScope.launch {
            effect.emit(LoginEffect.ShowErrorScreen(error.localizedMessage ?: ""))
        }
    }


}