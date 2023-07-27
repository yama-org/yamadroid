package com.yama.ui.screen.information.ui

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yama.ui.scaffold.ScaffoldTopBar
import com.yama.ui.screen.episodes.EpisodeViewModel
import com.yama.ui.screen.viewmodel.MainViewModel
import com.yama.ui.theme.textColor

@Composable
fun InformationContentView(
    context: Context,
    mainViewModel: MainViewModel,
    episodeViewModel: EpisodeViewModel,
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ScaffoldTopBar(
                scaffoldState = scaffoldState,
                scope = scope,
                mainViewModel = mainViewModel,
                episodeViewModel = episodeViewModel,
                navController = navController
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = 2.5.dp, end = 2.5.dp, top = 10.dp, bottom = 2.dp)
        ) {

            Column {
                CenterInformationBox(mainViewModel = mainViewModel)
            }

        }
    }
}

@Composable
fun CenterInformationBox(mainViewModel: MainViewModel) {

    val animeImage = mainViewModel.getAnime().thumbnail
    val animeTitle = mainViewModel.getAnime().title
    val animeDescription = mainViewModel.getAnime().description
    val animeEpisodesLength = mainViewModel.getAnime().episodes.size
    val animeGenres = mainViewModel.getAnime().genres
    val animeStudio = " "

    Column(
        Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 10.dp)
            .border(
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = animeImage.toInt()),
            contentDescription = "Image from :$animeTitle",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        LazyColumn(Modifier.padding(horizontal = 10.dp)) {

            item {
                TextInformation(title = "Description", data = animeDescription, true)
            }


            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                )
            }


            item {
                TextInformation(title = "Genres", data = animeGenres.toString(), false)
            }


            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                )
            }

            item {
                TextInformation(title = "Studio", data = animeStudio, false)
            }

        }
    }
}

@Composable
private fun TextInformation(title: String, data: String, isExpandable: Boolean) {

    var showMore by remember {
        mutableStateOf(false)
    }

    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textDecoration = TextDecoration.Underline
    )

    if (isExpandable) {


        Column(
            modifier = Modifier
                .animateContentSize(animationSpec = tween(100))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { showMore = !showMore }
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {

            if (showMore) {
                Text(
                    text = data,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

            } else {
                Text(
                    text = data,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {
            Text(
                text = data,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }


}
