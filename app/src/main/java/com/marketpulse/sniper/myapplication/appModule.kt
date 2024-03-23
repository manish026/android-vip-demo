package com.marketpulse.sniper.myapplication

import com.marketpulse.sniper.myapplication.data.remotestores.LoginServiceImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginInputValidatorImpl
import com.marketpulse.sniper.myapplication.domain.login.LoginService
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCase
import com.marketpulse.sniper.myapplication.domain.login.LoginUseCaseImpl
import com.marketpulse.sniper.myapplication.presenter.LoginPresenter
import com.marketpulse.sniper.myapplication.presenter.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    factory<LoginUseCase> {
        LoginUseCaseImpl(get(), LoginInputValidatorImpl(), get())
    }

    single<LoginService> {
        LoginServiceImpl()
    }

    viewModel<LoginViewModel> { LoginViewModel() }
    factory<LoginPresenter> { LoginViewModel() }
}