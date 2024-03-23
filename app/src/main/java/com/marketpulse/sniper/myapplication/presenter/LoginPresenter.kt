package com.marketpulse.sniper.myapplication.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

sealed interface LoginEffect {
    data object ShowDashboard: LoginEffect
    data class ShowErrorScreen(val error: String): LoginEffect
}

abstract class LoginPresenter {
    abstract val state: StateFlow<LoginState>
    abstract val effect: SharedFlow<LoginEffect>

    abstract fun setInvalidPassword()
    abstract fun setPassword(password: String)
    abstract fun setInvalidUsername()
    abstract fun setUsername(username: String)
    abstract fun loginSuccess()
    abstract fun loginFailure(error: Throwable)

}