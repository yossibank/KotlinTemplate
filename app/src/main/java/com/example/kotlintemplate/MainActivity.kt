package com.example.kotlintemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kotlintemplate.route.Route
import com.example.kotlintemplate.screen.DetailScreen
import com.example.kotlintemplate.screen.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Route.Home
            ) {
                composable<Route.Home> {
                    HomeScreen(
                        onClickDetail = {
                            navController.navigate(Route.Detail(id = 1))
                        }
                    )
                }
                composable<Route.Detail> {
                    val detail: Route.Detail = it.toRoute()
                    DetailScreen(
                        id = detail.id,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}
