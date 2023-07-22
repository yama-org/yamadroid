package com.yama.ui.screen.episodes.ui

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yama.R
import com.yama.domain.classes.Episode
import com.yama.ui.scaffold.ScaffoldBottomBar
import com.yama.ui.scaffold.ScaffoldSearchTopBar
import com.yama.ui.scaffold.ScaffoldTopBar
import com.yama.ui.screen.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EpisodesContentView(
    context: Context,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val isClicked by mainViewModel.isClicked.collectAsState()
    val isPressed by mainViewModel.isEpisodeLongClicked.collectAsState()

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
                    mainViewModel = mainViewModel
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isPressed,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                ScaffoldBottomBar(mainViewModel = mainViewModel)
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        drawerBackgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = 2.5.dp, end = 2.5.dp, top = 10.dp, bottom = 2.dp)
        ) {
            Column {
                CenterEpisodesBox(
                    mainViewModel = mainViewModel,
                    navController = navController,
                    scope = scope
                )
            }
        }
    }

}

@Composable
fun CenterEpisodesBox(
    mainViewModel: MainViewModel,
    navController: NavController,
    scope: CoroutineScope
) {
    Column(
        Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 10.dp)
            .border(
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        RecyclerViewEpisodes(mainViewModel, navController = navController, scope = scope)
    }

}

@Composable
fun RecyclerViewEpisodes(
    mainViewModel: MainViewModel,
    navController: NavController,
    scope: CoroutineScope
) {

    val episodes by mainViewModel.episode.collectAsState()

    if (episodes.isEmpty()) {
        Column(
            Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.yama_noepisodes),
                contentDescription = "no episodes",
                modifier = Modifier
                    .size(width = 250.dp, height = 300.dp)
                    .padding(end = 20.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
                alpha = 5f
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Text(
                text = "Sorry... No episodes found",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        LazyColumn(
            Modifier
                .padding(5.dp)
                .fillMaxHeight()
        ) {
            items(episodes) { item ->

                Log.d("EPISODE", "Index: $item")
                ItemEpisode(
                    item = item,
                    navController = navController,
                    mainViewModel = mainViewModel,
                    scope = scope
                )
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ItemEpisode(
    item: Episode,
    navController: NavController,
    mainViewModel: MainViewModel,
    scope: CoroutineScope
) {

    val isPressed by mainViewModel.isEpisodeLongClicked.collectAsState()

    AnimatedContent(
        targetState = isPressed,
        transitionSpec = { fadeIn() with fadeOut() }
    ) {

        if (isPressed) {

            EpisodeCard(
                item = item,
                navController = navController,
                mainViewModel = mainViewModel,
                scope = scope,
                color = MaterialTheme.colorScheme.inversePrimary
            )

        } else {

            EpisodeCard(
                item = item,
                navController = navController,
                mainViewModel = mainViewModel,
                scope = scope,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeCard(
    item: Episode,
    navController: NavController,
    mainViewModel: MainViewModel,
    scope: CoroutineScope,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(
                BorderStroke(3.dp, color = color),
                shape = RoundedCornerShape(10.dp)
            )
            .combinedClickable(
                onClick = {/*Navegacion*/ },
                onLongClick = {
                    scope.launch {
                        mainViewModel.isEpisodeLongClicked()
                        /*Logica de */
                    }
                })

    ) {
        Image(
            painter = painterResource(id = item.thumbnail.toInt()),
            contentDescription = "title image",
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
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

