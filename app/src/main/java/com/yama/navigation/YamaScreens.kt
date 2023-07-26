package com.yama.navigation

sealed class YamaScreens(val route:String) {

    object Home:YamaScreens("home")
    object Episodes:YamaScreens("episodes")
    object Information:YamaScreens("information")
    object Settings:YamaScreens("settings")
    object About:YamaScreens("about")


}