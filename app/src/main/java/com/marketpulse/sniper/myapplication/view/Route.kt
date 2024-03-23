package com.marketpulse.sniper.myapplication.view

sealed class Route(
    var key: String,
    val clearBackStack: Boolean = false
) {

    data object LoginRoute: Route("login")
    data object DashboardRoute: Route("dashboard", true)
    data class ErrorRoute(val error: String): Route("error/${error}") {
        companion object {
            const val message = "message"
            const val key = "error/{$message}"
        }
    }
    
}