package com.pdm.pe_fedorento.Produto.ViewProduto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm.pe_fedorento.Produto.ViewProduto.ui.theme.Pe_fedorentoTheme

class TelaProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pe_fedorentoTheme {
                // A surface container using the 'background' color from the theme
                menuProduto()
            }
        }
    }
}

@Composable
fun menuProduto(){


    val contexto = LocalContext.current

    val activity = (contexto as? Activity)

    Column(Modifier.padding(40.dp)) {

        Text(text = "Produtos", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(35.dp))

        Button(onClick = {
            contexto.startActivity(Intent(contexto, TelaInserirProduto::class.java))

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Inserir")
        }

        Spacer(modifier = Modifier.height(15.dp),)
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Mostrar")
        }

        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Atualizar")
        }

        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Excluir")
        }

        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }
    }

}

