package com.marketpulse.sniper.myapplication.data.remotestores

import com.marketpulse.sniper.myapplication.domain.login.LoginService

const val ADMIN = "admin"

class LoginServiceImpl: LoginService {

    class InvalidUsernamePassword: Throwable("Invalid username & password")

    override fun login(username: String, password: String): Result<Unit> {
        return if (username == ADMIN && password == ADMIN) {
            Result.success(Unit)
        } else {
            Result.failure(InvalidUsernamePassword())
        }
    }

}