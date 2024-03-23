package com.marketpulse.sniper.myapplication.presenter

data class LoginState(
    val username: String,
    val password: String,
    val userNameError: Boolean,
    val passwordError: Boolean
) {
    companion object {

        fun empty(): LoginState {
            return LoginState("", "", false, false)
        }

    }

}