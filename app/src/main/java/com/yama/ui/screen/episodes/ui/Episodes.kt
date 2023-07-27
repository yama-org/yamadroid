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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.yama.ui.screen.episodes.EpisodeViewModel
import com.yama.ui.screen.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EpisodesContentView(
    context: Context,
    mainViewModel: MainViewModel,
    navController: NavController,
    episodeViewModel: EpisodeViewModel
) {

    episodeViewModel.getEpisodes(mainViewModel.getAnime())
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val isClicked by episodeViewModel.isClicked.collectAsState()
    val isBottomBarActive by episodeViewModel.isBottomBarActive.collectAsState()

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
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarActive,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                ScaffoldBottomBar(episodeViewModel = episodeViewModel, scope = scope)
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
                    episodeViewModel = episodeViewModel,
                    navController = navController,
                    scope = scope
                )
            }
        }
    }

}

@Composable
fun CenterEpisodesBox(
    episodeViewModel: EpisodeViewModel,
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
        RecyclerViewEpisodes(
            episodeViewModel = episodeViewModel,
            navController = navController,
            scope = scope
        )
    }

}

@Composable
fun RecyclerViewEpisodes(
    navController: NavController,
    scope: CoroutineScope,
    episodeViewModel: EpisodeViewModel
) {

    val episodes by episodeViewModel.episode.collectAsState()

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

            itemsIndexed(episodes) { index, item ->

                EpisodeCard(
                    item = item,
                    navController = navController,
                    episodeViewModel = episodeViewModel,
                    scope = scope,
                    index = index
                )
            }
        }
    }

}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class
)
@Composable
fun EpisodeCard(
    item: Episode,
    navController: NavController,
    scope: CoroutineScope,
    index: Int,
    episodeViewModel: EpisodeViewModel
) {

    var isSelected by rememberSaveable { mutableStateOf(item.isSelected) }


    AnimatedContent(targetState = isSelected, label = "", content = {

        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(
                            3.dp,
                            color = MaterialTheme.colorScheme.inversePrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .combinedClickable(
                        onClick = {/*Navegacion*/ },
                        onLongClick = {
                            scope.launch {

                                episodeViewModel.activeBottomBar()
                                isSelected = episodeViewModel.episodeSelected(index)
                                episodeViewModel.bottombarCheck()
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
                    colorFilter = if (item.watched) ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(
                            0f
                        )
                    }) else ColorFilter.colorMatrix(ColorMatrix().apply { })
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
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(
                            3.dp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .combinedClickable(
                        onClick = {/*Navegacion*/ },
                        onLongClick = {
                            scope.launch {

                                episodeViewModel.activeBottomBar()
                                episodeViewModel.episodeSelected(index)
                                episodeViewModel.bottombarCheck()
                                isSelected = !isSelected

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
                    colorFilter = if (item.watched) ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(
                            0f
                        )
                    }) else ColorFilter.colorMatrix(ColorMatrix().apply { })
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
    },
        transitionSpec = { fadeIn() with fadeOut() }
    )
}

