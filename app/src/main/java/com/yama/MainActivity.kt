package com.yama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yama.ui.theme.YamaTheme
import com.yama.ui.screen.home.ui.ContentView
import com.yama.ui.screen.home.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YamaTheme(darkTheme = true) {
                ContentView(homeViewModel = HomeViewModel(), context = this@MainActivity)
            }
        }
    }
}
