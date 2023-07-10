package com.pdm.pe_fedorento.Cliente.ViewCliente

import android.app.Activity
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Cliente.ModelCliente
import kotlin.math.log


class TelaClienteMostrar : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val activity=(LocalContext.current as? Activity)
            MostrarClientes()

        }
    }
}

@Composable
fun MostrarClientes() {
    val clientes = remember { mutableStateListOf<ModelCliente>() }
    val activity=(LocalContext.current as? Activity)
    val db = FirebaseFirestore.getInstance()
    var expanded = false
    val contexto = LocalContext.current
    // Carrega os clientes do Firestore
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
                //val cliente = document.toObject(ModelCliente::class.java
            }
        }



    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(clientes.size) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)

            ) {

                Text(
                    text = "${clientes[index].nome}",
                    style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "CPF: ${clientes[index].cpf}",
                    style = TextStyle(fontSize = 14.sp, ),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Telefone: ${clientes[index].telefone}",
                    style = TextStyle(fontSize = 14.sp),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            var passaNome = clientes[index].nome
                            Toast.makeText(contexto, "Cliente selecionado: ${clientes[index].cpf}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(contexto, TelaClienteUpdate::class.java)
                            intent.putExtra("nome", passaNome)
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
                            db.collection("cliente").document( clientes[index].nome).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(contexto, "Cliente excluído com sucesso", Toast.LENGTH_LONG).show()
                                    activity?.finish()
                                    contexto.startActivity(Intent(contexto, TelaClienteMostrar::class.java))
                                }
                                .addOnFailureListener {
                                    Toast.makeText(contexto, "Falha ao excluir o cliente: $it", Toast.LENGTH_LONG).show()
                                    Log.d("Exclusão do cliente", it.toString())
                                }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Deletar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            var passaCpf = clientes[index].cpf
                            Toast.makeText(contexto, "Cliente selecionado: ${clientes[index].cpf}", Toast.LENGTH_SHORT).show()
                            // Implemente a lógica de exclusão aqui
                        },
                        modifier = Modifier.weight(1f)

                    ) {
                        Text("Comprar")
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


    }
    Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Voltar")
    }
}

