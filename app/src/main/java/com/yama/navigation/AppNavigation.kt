package com.yama.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yama.ui.screen.episodes.EpisodeViewModel
import com.yama.ui.screen.episodes.ui.EpisodesContentView
import com.yama.ui.screen.home.ui.HomeContentView
import com.yama.ui.screen.viewmodel.MainViewModel

@Composable

fun AppNavigation(
    mainViewModel: MainViewModel,
    context: Context,
    episodeViewModel: EpisodeViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = YamaScreens.Home.route) {
        composable(route = YamaScreens.Home.route) {
            mainViewModel.screenUbication = "Titles"
            HomeContentView(
                mainViewModel = mainViewModel,
                episodeViewModel = episodeViewModel,
                context = context,
                navController = navController
            )
        }

        composable(route = YamaScreens.Episodes.route) {
            mainViewModel.screenUbication = "Episodes"
            EpisodesContentView(
                context = context,
                mainViewModel = mainViewModel,
                episodeViewModel = episodeViewModel,
                navController = navController
            )
        }

        composable(route = YamaScreens.Information.route) {
            mainViewModel.screenUbication = "Information"
            /*Information.kt*/
        }

        composable(route = YamaScreens.Settings.route) {
            mainViewModel.screenUbication = "Settings"
            /*Settings.kt*/
        }

        composable(route = YamaScreens.About.route) {
            mainViewModel.screenUbication = "About"
            /*About.kt*/
        }
    }


}