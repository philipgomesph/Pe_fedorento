package com.pdm.pe_fedorento.Cliente.ViewCliente

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.pe_fedorento.MainActivity

class TelaCliente: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElementosDaTela()
        }
    }
}

@Composable
fun ElementosDaTela() {

    val activity=(LocalContext.current as? Activity)

    val contexto: Context = LocalContext.current


    Column(Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Clientes",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(300.dp)
                .padding(vertical = 16.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,

            )
        )
        Spacer(modifier = Modifier.weight(1f, true))
        Button(
            onClick = {

                contexto.startActivity(Intent(contexto, TelaClienteInserir::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Inserir Cliente")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {

                contexto.startActivity(Intent(contexto, TelaClienteMostrar::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Gerenciar Clientes")
        }

        Spacer(modifier = Modifier.weight(1f, true))
        Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }

    }
}