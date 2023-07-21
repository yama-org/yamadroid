package com.yama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.yama.navigation.AppNavigation
import com.yama.ui.theme.YamaTheme
import com.yama.ui.screen.home.ui.HomeContentView
import com.yama.ui.screen.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainViewModel by viewModels<MainViewModel>()

        super.onCreate(savedInstanceState)
        setContent {
            YamaTheme(darkTheme = true) {
                AppNavigation(mainViewModel = mainViewModel, context = applicationContext)
            }
        }
    }
}
