package com.udb.gradesapp.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.udb.gradesapp.controller.GradeController
import com.udb.gradesapp.model.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(
    onShowResult: (Double, String) -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val gradeController = GradeController()

    var studentName by remember { mutableStateOf("") }
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notas") },
                actions = {
                    IconButton(onClick = { activity.finish() }) {
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
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "Notas del estudiante",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Ingresa mínimo 3 notas para calcular el promedio.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
            )

            OutlinedTextField(
                value = studentName,
                onValueChange = { studentName = it },
                label = { Text("Nombre del estudiante") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = grade1,
                onValueChange = { grade1 = it },
                label = { Text("Nota 1") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = grade2,
                onValueChange = { grade2 = it },
                label = { Text("Nota 2") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = grade3,
                onValueChange = { grade3 = it },
                label = { Text("Nota 3") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (
                        studentName.isBlank() ||
                        grade1.isBlank() ||
                        grade2.isBlank() ||
                        grade3.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Completa todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val grades = listOfNotNull(
                        grade1.toDoubleOrNull(),
                        grade2.toDoubleOrNull(),
                        grade3.toDoubleOrNull()
                    )

                    if (grades.size < 3) {
                        Toast.makeText(
                            context,
                            "Las notas deben ser números válidos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    if (!gradeController.validateGrades(grades)) {
                        Toast.makeText(
                            context,
                            "Las notas deben estar entre 0 y 10",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val student = Student(
                        name = studentName,
                        grades = grades
                    )

                    val average = gradeController.calculateAverage(student)
                    val result = gradeController.getResult(average)

                    onShowResult(average, result)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Calcular promedio")
            }
        }
    }
}