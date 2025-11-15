package com.example.kotlintemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kotlintemplate.ui.route.Route
import com.example.kotlintemplate.ui.screen.DetailScreen
import com.example.kotlintemplate.ui.screen.home.HomeScreen
import com.example.kotlintemplate.ui.screen.rakuten.RakutenScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var awesome: Awesome

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
                        viewModel = hiltViewModel(),
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
        awesome.hello()
    }
}

interface Awesome {
    fun hello()
}

interface Another {
    fun world()
}

class AwesomeClass @Inject constructor(
    private val another: Another
) : Awesome {
    override fun hello() {
        println("Hello!")
        another.world()
    }
}

class AnotherClass @Inject constructor() : Another {
    override fun world() {
        println("World!")
    }
}

@Module
@InstallIn(SingletonComponent::class)
class AwesomeModule {
    @Provides
    fun provideAwesome(
        awesomeClass: AwesomeClass
    ): Awesome {
        return awesomeClass
    }
}

@Module
@InstallIn(SingletonComponent::class)
class AnotherModule {
    @Provides
    fun provideAnother(
        anotherClass: AnotherClass
    ): Another {
        return anotherClass
    }
}