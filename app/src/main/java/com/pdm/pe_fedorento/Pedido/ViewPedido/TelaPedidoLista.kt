package com.pdm.pe_fedorento.Pedido.ViewPedido

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Cliente.ModelCliente
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteComprar
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteMostrar
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteUpdate
import com.pdm.pe_fedorento.Pedido.ModelPedido
import com.pdm.pe_fedorento.Pedido.ViewPedido.ui.theme.Pe_fedorentoTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TelaPedidoLista : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        val nomeCliente = intent.getStringExtra("nome")

        setContent {
            Pe_fedorentoTheme {
                 Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                     listaPedidos(nomeCliente,)
                }
            }
        }
    }
}

@Composable
fun listaPedidos(nomeCliente: String?) {
    val contexto = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val activity = (LocalContext.current as? Activity)
    val clientes = remember { mutableStateListOf<ModelCliente>() }
    val pedidos = remember { mutableStateListOf<ModelPedido>() }
    val listaTeste= remember { mutableStateListOf<ModelPedido>() }

    if (nomeCliente == null) {
        Toast.makeText(contexto, "Nome = Null", Toast.LENGTH_SHORT).show()
        activity?.finish()
    }

    // Obter informações do cliente
    db.collection("cliente")
        .whereEqualTo("nome", nomeCliente)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val clienteData = document.data
                val clienteProcurado = ModelCliente(
                    cpf = clienteData["cpf"].toString(),
                    nome = clienteData["nome"].toString(),
                    telefone = clienteData["telefone"].toString(),
                    endereco = clienteData["endereco"].toString(),
                    instagram = clienteData["instagram"].toString(),
                    id_pedido = clienteData["id_pedido"] as? ArrayList<String>
                )
                clientes.add(clienteProcurado)
            }
        }

    db.collection("pedidos")
        .whereEqualTo("nomeCliente", nomeCliente)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val pedidoData = document.data
                val timestamp = pedidoData["dia"] as Timestamp

                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(timestamp.toDate())

                val pedidoProcurado = ModelPedido(
                    id_pedido = pedidoData["id_pedido"].toString(),
                    dia = formattedDate,
                    nomeCliente = pedidoData["nomeCliente"].toString(),
                    id_produtos = pedidoData["id_produtos"] as? ArrayList<String>,

                )
                pedidos.add(pedidoProcurado)
            }
        }

    LazyColumn(modifier = Modifier.padding(14.dp)) {
        items(clientes.size) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {


                Text(
                    text = "${clientes[index].nome}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "CPF: ${clientes[index].cpf}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))

            }
        }
        items(pedidos.size) { index ->
            Text(
                text = "ID da compra: ${pedidos[index].id_pedido}",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Nome do Cliente: ${pedidos[index].nomeCliente}",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Produtos Comprados: ${pedidos[index].id_produtos}",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Dia: ${pedidos[index].dia}",
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        var passaIdPedido = pedidos[index].id_pedido
                        Toast.makeText(contexto, "Pedido selecionado: ${pedidos[index].id_pedido}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(contexto, TelaClienteComprar::class.java)
                        intent.putExtra("idPedido", passaIdPedido)
                        intent.putExtra("edit", true)
                        intent.putExtra("nome", nomeCliente)
                        contexto.startActivity(intent)
                        activity?.finish()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        pedidos[index].id_pedido?.let {
                            db.collection("pedidos").document(it).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(contexto, "Pedido excluído com sucesso", Toast.LENGTH_LONG).show()
                                    activity?.finish()
                                    contexto.startActivity(Intent(contexto, TelaPedidoLista::class.java))
                                }
                                .addOnFailureListener {
                                    Toast.makeText(contexto, "Falha ao excluir o pedido: $it", Toast.LENGTH_LONG).show()
                                    Log.d("Exclusão do pedido", it.toString())
                                }
                        }
                    },

                    ) {
                    Text("Deletar")
                }
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}