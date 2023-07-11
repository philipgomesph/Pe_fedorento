package com.pdm.pe_fedorento.Pedido.ViewPedido

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Cliente.ModelCliente
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaCliente
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteComprar
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteMostrar
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteUpdate
import com.pdm.pe_fedorento.Pedido.ViewPedido.ui.theme.Pe_fedorentoTheme
import com.pdm.pe_fedorento.Produto.ViewProduto.TelaProduto

class TelaListaClientePedidos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pe_fedorentoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MostrarClientesPedidos()
                }
            }
        }
    }
}



@Composable
fun MostrarClientesPedidos() {
    val clientes = remember { mutableStateListOf<ModelCliente>() }
    val activity=(LocalContext.current as? Activity)
    val db = FirebaseFirestore.getInstance()
    val contexto = LocalContext.current

    db.collection("cliente")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val clienteData = document.data
                val cliente = ModelCliente(
                    cpf = clienteData["cpf"].toString(),
                    nome = clienteData["nome"].toString(),
                    telefone = clienteData["telefone"].toString(),
                    endereco = clienteData["endereco"].toString(),
                    instagram = clienteData["instagram"].toString()
                )
                clientes.add(cliente)

            }
        }



    LazyColumn(
        modifier = Modifier.padding(14.dp)

    ) {
        items(clientes.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(

                    modifier = Modifier
                        .width(190.dp)
                        .padding(vertical = 10.dp)

                ) {

                    Text(
                        text = "${clientes[index].nome}",
                        style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Text(
                        text = "Endereço: ${clientes[index].endereco}",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Instagram: ${clientes[index].instagram}",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.weight(1f, true))
                }
                Button(
                    onClick = {
                        var passaNome = clientes[index].nome
                        Toast.makeText(contexto, "Cliente selecionado: ${clientes[index].nome}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(contexto, TelaPedidoLista::class.java)
                        intent.putExtra("nome", passaNome)
                        contexto.startActivity(intent)
                        activity?.finish()
                    },
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Ver Pedidos")
                }
            }


        }

    }
}



