package com.marketpulse.sniper.myapplication.domain.login

sealed interface LoginAction {
    data class UsernameTyped(val username: String): LoginAction
    data class PasswordTyped(val password: String): LoginAction
    data class LoginClicked(val username: String, val password: String): LoginAction
}