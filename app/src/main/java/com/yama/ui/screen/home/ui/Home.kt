package com.yama.ui.screen.home.ui

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yama.domain.classes.Anime
import com.yama.navigation.YamaScreens
import com.yama.ui.scaffold.ScaffoldDrawer
import com.yama.ui.scaffold.ScaffoldSearchBar
import com.yama.ui.scaffold.ScaffoldSearchTopBar
import com.yama.ui.scaffold.ScaffoldTopBar
import com.yama.ui.screen.episodes.EpisodeViewModel
import com.yama.ui.screen.viewmodel.MainViewModel


@Composable
fun HomeContentView(
    mainViewModel: MainViewModel,
    episodeViewModel: EpisodeViewModel,
    context: Context,
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navigationItems = listOf(DrawerLocation.Settings, DrawerLocation.About)
    val isClicked by mainViewModel.isClicked.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AnimatedVisibility(
                visible = !isClicked,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                ScaffoldTopBar(
                    scaffoldState = scaffoldState,
                    scope = scope,
                    mainViewModel = mainViewModel,
                    episodeViewModel = episodeViewModel,
                    navController = navController
                )
            }
            AnimatedVisibility(
                visible = isClicked,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
            ) {
                ScaffoldSearchTopBar(
                    scope = scope,
                    mainViewModel = mainViewModel,
                    episodeViewModel = episodeViewModel
                )
            }
        },
        drawerContent = { ScaffoldDrawer(menuItems = navigationItems) },
        backgroundColor = MaterialTheme.colorScheme.background,
        drawerBackgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = 2.5.dp, end = 2.5.dp, top = 10.dp, bottom = 2.dp)
        ) {
            Column() {
                CenterTitlesBox(mainViewModel = mainViewModel, navController = navController)
            }
        }
    }

}


@Composable
fun CenterTitlesBox(mainViewModel: MainViewModel, navController: NavController) {
    Column(
        Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 10.dp)
            .border(
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            ),
        verticalArrangement = Arrangement.Center
    ) {
        RecyclerViewTitles(mainViewModel, navController = navController)
    }

}

@Composable
private fun RecyclerViewTitles(mainViewModel: MainViewModel, navController: NavController) {

    val anime by mainViewModel.anime.collectAsState()

    LazyColumn(
        Modifier
            .padding(5.dp)
            .fillMaxHeight()
    ) {
        items(anime) { item ->
            ItemTitle(item = item, navController = navController, mainViewModel = mainViewModel)
        }
    }
}

@Composable
private fun ItemTitle(item: Anime, navController: NavController, mainViewModel: MainViewModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(
                BorderStroke(3.dp, color = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                mainViewModel.animeSelected(item)
                if (mainViewModel.isClicked.value) {
                    mainViewModel.isClicked()
                }
                mainViewModel.emptySearch()
                navController.navigate(route = YamaScreens.Episodes.route)
                Log.d("Home_TAG", "Tocaste ${item.title}")
            }
    ) {
        Image(
            painter = painterResource(id = item.thumbnail.toInt()),
            contentDescription = "title image",
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
                .safeContentPadding()
                .padding(vertical = 8.dp, horizontal = 15.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

