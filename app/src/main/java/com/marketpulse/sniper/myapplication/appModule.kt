package com.marketpulse.sniper.myapplication

import com.marketpulse.sniper.myapplication.data.remotestores.LoginServiceImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginAction
import com.marketpulse.sniper.myapplication.domain.login.LoginInputValidatorImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginService
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCaseImpl
import com.marketpulse.sniper.myapplication.domain.login.UseCase
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter
import com.marketpulse.sniper.myapplication.presenter.LoginViewModel
import com.marketpulse.sniper.myapplication.view.Route
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.ScopeDSL
import org.koin.dsl.module

val appModule = module{

    factory<UseCase<LoginPresenter, LoginAction>> {
        LoginUseCaseImpl(get(), LoginInputValidatorImpl(), get())
    }

    scope(Route.LoginRoute) {
        scoped<UseCase<LoginPresenter, LoginAction>> { LoginUseCaseImpl(get(), LoginInputValidatorImpl(), get()) }
    }

    scope(Route.ErrorRoute.key) {
        scoped<UseCase<LoginPresenter, LoginAction>> { LoginUseCaseImpl(get(), LoginInputValidatorImpl(), get()) }
    }

    single<LoginService> {
        LoginServiceImpl()
    }


    factory<LoginPresenter> { LoginViewModel() }
}

fun Module.scope(route: Route, scopeSet: ScopeDSL.() -> Unit) {
    scope(named(route.key), scopeSet)
}

fun Module.scope(key: String, scopeSet: ScopeDSL.() -> Unit) {
    scope(named(key), scopeSet)
}