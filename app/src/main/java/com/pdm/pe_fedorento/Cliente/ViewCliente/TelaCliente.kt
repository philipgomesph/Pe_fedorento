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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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


    val cpfTextField = remember{ mutableStateOf(TextFieldValue()) }
    val nomeTextField = remember{ mutableStateOf(TextFieldValue()) }
    val telefoneTextField = remember{ mutableStateOf(TextFieldValue()) }
    val enderecoTextField = remember{ mutableStateOf(TextFieldValue()) }
    val instagramTextField = remember{ mutableStateOf(TextFieldValue()) }


    Column(Modifier.padding(40.dp)) {
        Text(text = "Clientes", textAlign = TextAlign.Center, modifier = Modifier.width(300.dp))
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {

                contexto.startActivity(Intent(contexto, TelaClienteInserir::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Inserir Cliente")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

                //contexto.startActivity(Intent(contexto, TelaMostrar::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Mostrar Clientes")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

                //contexto.startActivity(Intent(contexto, TelaAtualiza::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Alterar Clientes")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

                //contexto.startActivity(Intent(contexto, TelaDeletar::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Excluir Clientes")
        }

        Spacer(modifier = Modifier.weight(1f, true))
        Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }

    }
}