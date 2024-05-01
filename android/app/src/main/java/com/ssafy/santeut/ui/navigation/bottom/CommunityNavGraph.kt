package com.ssafy.santeut.ui.navigation.bottom

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.santeut.ui.community.CommunityScreen
import com.ssafy.santeut.ui.guild.GuildScreen


fun NavGraphBuilder.CommunityNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = "community",
        route = "community_graph"
    ) {
        composable("community") {
            CommunityScreen()
        }
    }
}