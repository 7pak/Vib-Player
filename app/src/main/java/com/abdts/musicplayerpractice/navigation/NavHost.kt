package com.abdts.musicplayerpractice.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abdts.musicplayerpractice.ui.UIEvents
import com.abdts.musicplayerpractice.ui.local.LocalAudioScreen
import com.abdts.musicplayerpractice.ui.local.LocalViewModel
import com.abdts.musicplayerpractice.ui.remote.RemoteAudioScreen
import com.abdts.musicplayerpractice.ui.setting.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController, onStarService: () -> Unit
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val bottomNavItems = listOf(
        BottomNavigationData(
            title = "Local",
            unselectedIcon = Icons.Outlined.Audiotrack,
            selectedIcon = Icons.Filled.Audiotrack,
        ),
        BottomNavigationData(
            title = "Online",
            unselectedIcon = Icons.Outlined.Explore,
            selectedIcon = Icons.Filled.Explore,
        )
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()


    LaunchedEffect(currentBackStackEntry) {
        selectedItemIndex = when (currentBackStackEntry?.destination?.route) {
            Screens.LocalAudioScreen.route -> 0
            Screens.RemoteAudioScreen.route -> 1
            else -> 0
        }
    }


    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(10.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black,
                            Color.Black
                        ),

                        label = {
                            Text(text = item.title)
                        },
                        selected = selectedItemIndex == index,
                        onClick = {
                            if (selectedItemIndex == index) return@NavigationBarItem else {
                                selectedItemIndex = index
                            }
                            when (selectedItemIndex) {
                                0 -> {
                                    navController.popBackStack()
                                }

                                else -> {
                                    navController.navigate(Screens.RemoteAudioScreen.route)
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null
                            )
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.LocalAudioScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.LocalAudioScreen.route) {
                val viewModel: LocalViewModel = koinViewModel()
                LocalAudioScreen(
                    localState = viewModel.localState,
                    audioList = viewModel.audioList,
                    onEvent = viewModel::onUIEvent,
                    onItemClicked = { index, state ->
                        viewModel.onUIEvent(UIEvents.SelectedAudioChange(index))
                        viewModel.updateState(state)
                        onStarService()
                    },
                    navController = navController
                )
            }
            composable(Screens.RemoteAudioScreen.route) {
                RemoteAudioScreen()
            }
            composable(Screens.SettingAudioScreen.route) {
                SettingsScreen()
            }
        }
    }
}

data class BottomNavigationData(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val playing: Boolean = false
)