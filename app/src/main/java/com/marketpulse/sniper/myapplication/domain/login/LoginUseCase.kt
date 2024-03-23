package com.marketpulse.sniper.myapplication.domain.login

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter


@Stable
abstract class UseCase<Presenter, Action>: ViewModel() {
    abstract val presenter: Presenter

    abstract fun perform(action: Action)
}