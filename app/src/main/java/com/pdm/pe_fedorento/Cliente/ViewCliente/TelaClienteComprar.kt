package com.pdm.pe_fedorento.Cliente.ViewCliente

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
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
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.ModelProduto.ModelProduto
import com.pdm.pe_fedorento.Produto.ViewProduto.TelaInserirProduto
import com.pdm.pe_fedorento.Produto.ViewProduto.TelaProdutoMostrar
import java.time.LocalDate
import java.util.*

class TelaClienteComprar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        val nomeCliente = intent.getStringExtra("nome")

        setContent {
            ElementosDaTelaCompra(nomeCliente)
        }
    }
}

@Composable
fun Cabeçalho(nomeCliente:String?){
    nomeCliente?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Olá ${nomeCliente}, o que vai ser hoje?" ,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun Rodape(valorTotal:Double){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Valor Total: R$ $valorTotal",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ElementosDaTelaCompra(nomeCliente:String?) {

    val produtos = remember { mutableStateListOf<ModelProduto>() }
    val selectedItems = remember { mutableStateListOf<Boolean>() }
    val activity = (LocalContext.current as? Activity)
    val db = FirebaseFirestore.getInstance()
    val contexto = LocalContext.current
    val id_pedido = UUID.randomUUID().toString()

    db.collection("produto")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val produtoData = document.data
                val produto = ModelProduto(
                    id_produto = produtoData["id"].toString(),
                    descricao = produtoData["descricao"].toString(),
                    valor = produtoData["valor"].toString().toDouble(),
                    foto = produtoData["foto"].toString(),
                )
                produtos.add(produto)
                selectedItems.add(false)
            }
        }

    LazyColumn(
        modifier = Modifier.padding(14.dp)
    ) {
        item {
            Cabeçalho(nomeCliente)
        }
        items(produtos.size) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)

            ) {
                Text(
                    text = " ${produtos[index].descricao}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "ID: ${produtos[index].id_produto}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Valor: ${produtos[index].valor}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Foto: ${produtos[index].foto}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Checkbox(
                        checked = selectedItems[index],
                        onCheckedChange = { selectedItems[index] = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }


        }
        item {
            val valorTotal = selectedItems
                .mapIndexed { index, selected -> if (selected) produtos[index].valor else 0.0 }
                .sum()
            Rodape(valorTotal = valorTotal)
            val listaDeProdutos = selectedItems
                .mapIndexed { index, selected -> if (selected) produtos[index].id_produto else null }
                .filterNotNull()

            Button(


                onClick = {
                    val pedido = hashMapOf(
                        "id_pedido" to id_pedido,
                        "dia" to Date(),
                        "nomeCliente" to nomeCliente,
                        "id_produtos" to listaDeProdutos,
                    )

                    val collectionRef = db.collection("pedidos")

                    db.collection("pedidos").document(id_pedido).set(pedido)
                        .addOnSuccessListener {
                            Toast.makeText(contexto, "Pedido feito com sucesso", Toast.LENGTH_LONG).show()


                            if (nomeCliente != null) {
                                val clienteRef = db.collection("cliente").document(nomeCliente)
                                clienteRef.get().addOnSuccessListener { clienteSnapshot ->
                                    val idPedidosAntigos = clienteSnapshot.get("id_pedido") as? List<String> ?: emptyList()
                                    val idPedidosAtualizados = idPedidosAntigos.toMutableList().apply {
                                        add(id_pedido)
                                    }

                                    clienteRef
                                        .update("id_pedido", idPedidosAtualizados)
                                        .addOnSuccessListener {
                                            Log.d("it", "Cliente atualizado com sucesso")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e("it", "Erro ao atualizar o campo 'id_pedido' do cliente", e)
                                        }
                                }.addOnFailureListener { e ->
                                    Log.e("it", "Erro ao obter o documento do cliente", e)
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(contexto, "Falha ao realizar o pedido", Toast.LENGTH_LONG).show()
                            Log.e("it", "Falha ao adicionar o pedido", e)
                        }

                    activity?.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text("Realizar Pedido")
            }
        }
    }


}