package com.pdm.pe_fedorento

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaCliente
import com.pdm.pe_fedorento.Pedido.ViewPedido.TelaListaClientePedidos
import com.pdm.pe_fedorento.Produto.ViewProduto.TelaProduto
import com.pdm.pe_fedorento.ui.theme.Pe_fedorentoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElementosDaTela()
        }
    }
}




@Composable
fun ElementosDaTela(){
    val contexto: Context = LocalContext.current
    Column(Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Pé Fedorento LTDA",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(300.dp)
                .padding(vertical = 16.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
        )


        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {

                contexto.startActivity(Intent(contexto, TelaCliente::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Clientes")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

               contexto.startActivity(Intent(contexto, TelaProduto::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Produtos")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

                contexto.startActivity(Intent(contexto, TelaListaClientePedidos::class.java))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Fazer Pedido")
        }


        Spacer(modifier = Modifier.height(35.dp))
        val imageModifier= Modifier
            .width(300.dp)
            .height(200.dp)
            .align(Alignment.CenterHorizontally)
            .background(
                Color.White
            )
        //Image(painter = painterResource(id= R.drawable.foto), contentDescription = "Brinquedos", modifier = imageModifier )
    }
}

