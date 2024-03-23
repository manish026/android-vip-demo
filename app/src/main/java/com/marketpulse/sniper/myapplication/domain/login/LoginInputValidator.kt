package com.marketpulse.sniper.myapplication.domain.login

interface LoginInputValidator {
    fun isValidUsername(username: String): Boolean
    fun isValidPassword(password: String): Boolean
}