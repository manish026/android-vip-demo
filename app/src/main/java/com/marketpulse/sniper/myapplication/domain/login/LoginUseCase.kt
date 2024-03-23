package com.marketpulse.sniper.myapplication.domain.login

import androidx.compose.runtime.Stable
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter

@Stable
interface LoginUseCase {
    val presenter: LoginPresenter

    fun perform(action: LoginAction)
}