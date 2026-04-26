package com.udb.gradesapp.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    userName: String,
    onContinue: () -> Unit
) {

    val activity = LocalContext.current as Activity

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Bienvenida")
                },

                actions = {

                    IconButton(
                        onClick = {
                            activity.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Salir"
                        )
                    }

                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(28.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Bienvenido, $userName",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "¿Listo para ingresar notas?",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Button(
                onClick = onContinue
            ) {
                Text("Ingresar notas")
            }

        }
    }
}