package com.yama.ui.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yama.R
import com.yama.navigation.YamaScreens
import com.yama.ui.screen.home.ui.DrawerLocation
import com.yama.ui.screen.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldTopBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    TopAppBar(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
        title = {
            Text(
                text = mainViewModel.screenUbication,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

        },
        navigationIcon = {

            when (mainViewModel.screenUbication) {

                "Titles" -> {
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
                }

                "Episodes" -> {
                    IconButton(
                        onClick = {
                            scope.launch {
                                navController.navigate(YamaScreens.Home.route)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back icon",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }

        },
        actions = {
            IconButton(onClick = {
                scope.launch {
                    mainViewModel.isClicked()
                }

            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(35.dp)
                )

            }
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.background
    )
}

@Composable
fun ScaffoldSearchBar(mainViewModel: MainViewModel) {

    val searchText by mainViewModel.searchText.collectAsState()
    val isClicked by mainViewModel.isClicked.collectAsState()


    AnimatedVisibility(
        visible = isClicked,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = { mainViewModel.isClicked() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(35.dp)
                )
            }

            TextField(
                value = searchText,
                onValueChange = mainViewModel::onSearchTextChange,
                placeholder = {
                    Text(
                        text = "Search title...",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.background,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                shape = RoundedCornerShape(12),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 15.dp)
            )
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

        Spacer(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth()
        )

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
            .height(60.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(12))
    ) {
        TextButton(onClick = { /*TODO*/ }) {

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

}