package com.yama.ui.scaffold

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
                                mainViewModel.isNotPressed()
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
            when (mainViewModel.screenUbication) {

                "Titles" -> {
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
                }

                "Episodes" -> {

                    IconButton(onClick = { /*Navegacion a informacion de la serie*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Info Icon",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))

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

                }

            }

        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.background
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldSearchTopBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val searchText by mainViewModel.searchText.collectAsState()

    CenterAlignedTopAppBar(
        title = { },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background),
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        mainViewModel.isClicked()
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
        },
        actions = { ScaffoldSearchBar(mainViewModel = mainViewModel) })

}

@Composable
fun ScaffoldSearchBar(mainViewModel: MainViewModel) {

    val searchText by mainViewModel.searchText.collectAsState()
    val isClicked by mainViewModel.isClicked.collectAsState()


    Row(
        modifier = Modifier
            .width(300.dp),
        horizontalArrangement = Arrangement.Center
    ) {

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

@Composable
fun ScaffoldBottomBar(mainViewModel: MainViewModel) {

    BottomAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(20)),
        containerColor = MaterialTheme.colorScheme.tertiary,
        tonalElevation = 50.dp,
        contentColor = MaterialTheme.colorScheme.onBackground,
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(80.dp),
                    )
                }

                IconButton(modifier = Modifier.size(60.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Check icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(80.dp),
                    )
                }

                IconButton(modifier = Modifier.size(60.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Checklist,
                        contentDescription = "CheckList icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(80.dp),
                    )
                }
            }

        }
    )


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