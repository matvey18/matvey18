package com.example.audiobooks.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.audiobooks.ui.navigation.NavRoute
import com.example.audiobooks.ui.screens.*

@Composable
fun AppScaffold(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavRoute.values().forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = { navController.navigate(item.route) },
                        icon = { Icon(Icons.DefaultFor(item), contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = NavRoute.Home.route, modifier = modifier.padding(paddingValues)) {
            composable(NavRoute.Home.route) { HomeScreen() }
            composable(NavRoute.Search.route) { SearchScreen() }
            composable(NavRoute.History.route) { HistoryScreen() }
            composable(NavRoute.Favorites.route) { FavoritesScreen() }
            composable(NavRoute.Links.route) { LinksScreen() }
            composable(NavRoute.Settings.route) { SettingsScreen() }
        }
    }
}

// Minimal icon helper without adding custom drawables
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
private fun Icons.DefaultFor(route: NavRoute): ImageVector = when (route) {
    NavRoute.Home -> Icons.Default.Home
    NavRoute.Search -> Icons.Default.Search
    NavRoute.History -> Icons.Default.History
    NavRoute.Favorites -> Icons.Default.Favorite
    NavRoute.Links -> Icons.Default.Link
    NavRoute.Settings -> Icons.Default.Settings
}