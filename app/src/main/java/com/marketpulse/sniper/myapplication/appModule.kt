package com.marketpulse.sniper.myapplication

import com.marketpulse.sniper.myapplication.data.remotestores.LoginServiceImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginAction
import com.marketpulse.sniper.myapplication.domain.login.LoginInputValidatorImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginService
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCaseImpl
import com.marketpulse.sniper.myapplication.domain.login.UseCase
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter
import com.marketpulse.sniper.myapplication.presenter.LoginViewModel
import org.koin.dsl.module

val appModule = module{

    factory<UseCase<LoginPresenter, LoginAction>> {
        LoginUseCaseImpl(get(), LoginInputValidatorImpl(), get())
    }

    single<LoginService> {
        LoginServiceImpl()
    }


    factory<LoginPresenter> { LoginViewModel() }
}