package com.pdm.pe_fedorento.Produto.ViewProduto

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Cliente.ModelCliente
import com.pdm.pe_fedorento.Cliente.ViewCliente.MostrarClientes
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteMostrar
import com.pdm.pe_fedorento.Cliente.ViewCliente.TelaClienteUpdate
import com.pdm.pe_fedorento.ModelProduto.ModelProduto
import com.pdm.pe_fedorento.Produto.ViewProduto.ui.theme.Pe_fedorentoTheme

class TelaProdutoMostrar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MostrarProdutos()
        }
    }
}

@Composable
fun MostrarProdutos() {
    val produtos = remember { mutableStateListOf<ModelProduto>() }
    val activity=(LocalContext.current as? Activity)
    val db = FirebaseFirestore.getInstance()
    val contexto = LocalContext.current

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

            }
        }


    LazyColumn(
        modifier = Modifier.padding(14.dp)
    ) {
        items(produtos.size) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)

            ) {
                Text(
                    text = " ${produtos[index].descricao}",
                    style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold),
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                            var passaId = produtos[index].id_produto
                            val intent = Intent(contexto, TelaInserirProduto::class.java)
                            intent.putExtra("id", passaId)
                            intent.putExtra("edit", true)
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
                            db.collection("produto").document( produtos[index].id_produto).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(contexto, "Produto excluído com sucesso", Toast.LENGTH_LONG).show()
                                    activity?.finish()
                                    contexto.startActivity(Intent(contexto, TelaProdutoMostrar::class.java))
                                }
                                .addOnFailureListener {
                                    Toast.makeText(contexto, "Falha ao excluir o produto: $it", Toast.LENGTH_LONG).show()
                                    Log.d("Exclusão do produto", it.toString())
                                }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Deletar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                }
            }
        }


    }




}