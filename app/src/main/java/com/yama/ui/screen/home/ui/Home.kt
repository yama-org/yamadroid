package com.yama.ui.screen.home.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.yama.R
import com.yama.ui.screen.home.repo.RepositorioDeTesteo
import com.yama.ui.screen.home.repo.TitlesTest
import com.yama.ui.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


val listTest = RepositorioDeTesteo.titlesTest


@Composable
fun ContentView(homeViewModel: HomeViewModel, context: Context) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navigationItems = listOf(DrawerLocation.Settings, DrawerLocation.About)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { ScaffoldTopBar(scaffoldState = scaffoldState, scope = scope) },
        drawerContent = { ScaffoldDrawer(menuItems = navigationItems) },
        backgroundColor = MaterialTheme.colorScheme.background,
        drawerBackgroundColor = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Spacer(modifier = Modifier.padding(vertical = 1.dp))
                CenterTitlesBox(homeViewModel)
            }
        }
    }

}

@Composable
fun ScaffoldDrawer(menuItems: List<DrawerLocation>) {

    val textVersion = "0.0.1"

    Column() {

        Image(
            painter = painterResource(id = R.drawable.yamaicon),
            contentDescription = "yama icon",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(
            modifier = Modifier
                .padding(top = 1.dp, bottom = 20.dp)
                .fillMaxWidth()
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )

        menuItems.forEach { item ->
            ScaffoldDrawerItem(menuItems = item)
        }

        Spacer(modifier = Modifier
            .height(340.dp)
            .fillMaxWidth())

        Text(
            text = "yama version $textVersion",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp)
        )

    }
}

@Composable
fun ScaffoldDrawerItem(menuItems: DrawerLocation) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
    ) {
        Icon(
            imageVector = menuItems.icon,
            contentDescription = "${menuItems.icon} icon",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.width(13.dp))

        Text(
            text = menuItems.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

    }

}

@Composable
fun ScaffoldTopBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {

    TopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Titles",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background
    )
}


@Composable
private fun CenterTitlesBox(homeViewModel: HomeViewModel) {
    Column(
        Modifier
            .padding(10.dp)
            .border(
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        RecyclerViewTitles(homeViewModel)
    }

}

@Composable
private fun RecyclerViewTitles(homeViewModel: HomeViewModel) {
    LazyColumn(
        Modifier
            .padding(5.dp)
    ) {
        items(listTest) { item ->
            ItemTitle(item = item)
        }
    }
}

@Composable
private fun ItemTitle(item: TitlesTest) {

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
                /*Navegación a otra pantalla*/
                Log.d("Home_TAG", "Tocaste ${item.name}")
            }
    ) {
        Image(
            painter = painterResource(id = item.thumbnail),
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
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}