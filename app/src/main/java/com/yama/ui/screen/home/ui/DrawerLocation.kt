package com.yama.ui.screen.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerLocation(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    object Settings : DrawerLocation(Icons.Filled.Settings, "Settings", "settings")
    object About : DrawerLocation(Icons.Filled.Info, "About", "about")
}
