package com.udb.gradesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.udb.gradesapp.ui.WelcomeScreen
import com.udb.gradesapp.ui.screens.GradesScreen
import com.udb.gradesapp.ui.screens.LoginScreen
import com.udb.gradesapp.ui.screens.RegisterScreen
import com.udb.gradesapp.ui.screens.ResultScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {

                composable("login") {

                    LoginScreen(
                        onLoginSuccess = { email ->
                            val userName = email.substringBefore("@")
                            navController.navigate("welcome/$userName")
                        },
                        onGoToRegister = {
                            navController.navigate("register")
                        }
                    )
                }

                composable("register") {

                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("login")
                        },
                        onGoToLogin = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("welcome/{userName}") { backStackEntry ->

                    val userName =
                        backStackEntry.arguments?.getString("userName")
                            ?: "User"

                    WelcomeScreen(
                        userName = userName,

                        onContinue = {
                            navController.navigate("grades")
                        }
                    )
                }

                composable("grades") {

                    GradesScreen(
                        onShowResult = { average, result ->

                            navController.navigate(
                                "result/$average/$result"
                            )
                        }
                    )
                }

                composable("result/{average}/{result}") { backStackEntry ->

                    val average = backStackEntry.arguments
                        ?.getString("average")
                        ?.toDoubleOrNull() ?: 0.0

                    val result = backStackEntry.arguments
                        ?.getString("result") ?: "Sin resultado"

                    ResultScreen(
                        average = average,
                        result = result,
                        onEnterOtherGrades = {
                            navController.navigate("grades")
                        }
                    )
                }
            }
        }
    }
}