package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A nossa tela principal agora controlará o estado de login
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    // --- ESTADOS DA TELA ---
    // 1. Estados para os campos de texto (sua lógica original)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 2. NOVO ESTADO: Controla se o usuário está logado ou não
    var isLoggedIn by remember { mutableStateOf(false) }

    // Definições fixas
    val context = LocalContext.current
    val CORRECT_USERNAME = "admin"
    val CORRECT_PASSWORD = "123"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // --- LÓGICA DE O QUE MOSTRAR NA TELA ---
        // Se o usuário estiver logado, mostra a tela de boas-vindas.
        // Senão, mostra o formulário de login.
        if (isLoggedIn) {
            // TELA DE BOAS-VINDAS (mostrada após o login)
            WelcomeScreen(
                username = username,
                onLogout = {
                    // Ao clicar em "Sair", muda o estado para falso, voltando ao login
                    isLoggedIn = true
                    username = "" // Limpa os campos
                    password = ""
                }
            )
        } else {
            // TELA DE LOGIN (seu código original, com uma pequena alteração)
            LoginForm(
                username = username,
                onUsernameChange = { username = it },
                password = password,
                onPasswordChange = { password = it },
                onLoginClick = {
                    if (username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
                        // Login bem-sucedido! Muda o estado para true.
                        // O Compose vai redesenhar a tela automaticamente.
                        isLoggedIn = true
                        Toast.makeText(context, "Login bem-sucedido! 🎉", Toast.LENGTH_SHORT).show()
                    } else {
                        // Login falhou
                        Toast.makeText(context, "Usuário ou senha incorretos. 🚫", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }
}

// Separamos o formulário de login em sua própria função para organizar o código
@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ---- LOGO (TEXTO) ----
        Text(
            text = "LEOO",
            color = Color.White,
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center

        )

        Text(
            text = "MARKETING CLOUD COMPANY",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // ---- CAMPO USUÁRIO ----
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Nome do usuário") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // ---- CAMPO SENHA ----
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // ---- BOTÃO ENTRAR ----
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "ENTRAR",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Função para a tela de boas-vindas
@Composable
fun WelcomeScreen(username: String, onLogout: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo, $username!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Sair", color = Color.Black)
        }
    }
}


// ---------- PREVIEW ----------
// O preview agora precisa mostrar os dois estados. Vamos criar um para cada.
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen() // Preview começa com a tela de login
    }
}