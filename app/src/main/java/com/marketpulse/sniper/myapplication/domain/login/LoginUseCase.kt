package com.marketpulse.sniper.myapplication.domain.login

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter


@Stable
interface UseCase<Presenter, Action> {
    val presenter: Presenter

    fun perform(action: Action)
}