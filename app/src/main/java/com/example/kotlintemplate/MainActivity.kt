package com.example.kotlintemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kotlintemplate.data.api.ApiClient
import com.example.kotlintemplate.data.api.ApiRepository
import com.example.kotlintemplate.ui.route.Route
import com.example.kotlintemplate.ui.screen.DetailScreen
import com.example.kotlintemplate.ui.screen.HomeScreen
import com.example.kotlintemplate.ui.screen.rakuten.RakutenScreen
import com.example.kotlintemplate.ui.screen.rakuten.RakutenViewModel

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
                        },
                        onClickRakuten = {
                            navController.navigate(Route.Rakuten)
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
                composable<Route.Rakuten> {
                    RakutenScreen(
                        viewModel = RakutenViewModel(
                            apiRepository = ApiRepository(
                                client = ApiClient()
                            )
                        ),
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}
