package com.udb.gradesapp.ui.screens

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
fun ResultScreen(
    average: Double,
    result: String,
    onEnterOtherGrades: () -> Unit
) {

    val activity = LocalContext.current as Activity

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Resultados")
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
                text = "Promedio: %.2f".format(average),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Estado: $result",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            Button(
                onClick = onEnterOtherGrades,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Ingresar otras notas")
            }

        }
    }
}