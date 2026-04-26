package com.udb.gradesapp.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onGoToLogin: () -> Unit
) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Regístrate para ver tus notas",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 32.dp
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                if(email.isBlank() || password.isBlank()){
                    Toast.makeText(
                        context,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(
                        context,
                        "Formato de correo inválido",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                if(password.length < 6){
                    Toast.makeText(
                        context,
                        "La contraseña debe tener al menos 6 caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                isLoading = true

                auth.createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener { task ->

                    isLoading = false

                    if(task.isSuccessful){

                        Toast.makeText(
                            context,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        onRegisterSuccess()

                    } else {

                        Toast.makeText(
                            context,
                            task.exception?.message ?: "Registro fallido",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            enabled = !isLoading
        ) {

            Text(
                if(isLoading)
                    "Creando cuenta..."
                else
                    "Registrarse"
            )
        }

        TextButton(
            onClick = onGoToLogin,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {

            Text(
                "¿Ya tienes cuenta? Inicia sesión"
            )
        }

    }
}