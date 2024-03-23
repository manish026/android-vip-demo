package com.marketpulse.sniper.myapplication.domain.login

interface LoginService {

    fun login(username: String, password: String): Result<Unit>

}