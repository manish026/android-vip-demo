package com.marketpulse.sniper.myapplication.domain.login

class LoginInputValidatorImpl: LoginInputValidator {
    
    override fun isValidUsername(username: String): Boolean {
        return username.count() <= 7
    }

    override fun isValidPassword(password: String): Boolean {
        return password.count() <= 10
    }

}