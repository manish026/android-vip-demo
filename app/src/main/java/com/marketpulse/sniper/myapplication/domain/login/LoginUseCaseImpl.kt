package com.marketpulse.sniper.myapplication.domain.login

import com.marketpulse.sniper.myapplication.presenter.LoginPresenter

class LoginUseCaseImpl(
    override val presenter: LoginPresenter,
    private val loginInputValidator: LoginInputValidator,
    private val repository: LoginService
): UseCase<LoginPresenter, LoginAction> {


    override fun perform(action: LoginAction) {
        when(action) {

            is LoginAction.PasswordTyped -> {
                onPasswordChanged(action)
            }
            is LoginAction.UsernameTyped -> {
                onUsernameChanged(action)
            }

            is LoginAction.LoginClicked ->  {
                login(action)
            }
        }
    }

    private fun login(action: LoginAction.LoginClicked) {
        val isValidPassword = loginInputValidator.isValidPassword(action.password)
        val isValidUsername = loginInputValidator.isValidUsername(action.username)
        if (!isValidPassword || action.password.isBlank()) {
            presenter.setInvalidPassword()
        }
        if (!isValidUsername || action.username.isBlank()) {
            presenter.setInvalidUsername()
        }
        if (isValidUsername && isValidPassword) {
            val result = repository.login(action.username, action.password)
            result.onSuccess {
                presenter.loginSuccess()
            }
            result.onFailure {
                presenter.loginFailure(it)
            }
        }
    }

    private fun onUsernameChanged(action: LoginAction.UsernameTyped) {
        if (!loginInputValidator.isValidUsername(action.username)) {
            presenter.setInvalidUsername()
        } else {
            presenter.setUsername(action.username)
        }
    }

    private fun onPasswordChanged(action: LoginAction.PasswordTyped) {
        if (!loginInputValidator.isValidPassword(action.password)) {
            presenter.setInvalidPassword()
        } else {
            presenter.setPassword(action.password)
        }
    }

}