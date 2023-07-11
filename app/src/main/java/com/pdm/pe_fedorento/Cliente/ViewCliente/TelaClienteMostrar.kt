package com.pdm.pe_fedorento.Cliente.ViewCliente

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Cliente.ModelCliente
import com.pdm.pe_fedorento.Shared.voltaTela
import com.pdm.pe_fedorento.ui.theme.Purple500
import kotlin.math.log


class TelaClienteMostrar : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MostrarClientes()
        }
    }
}

@Composable
fun MostrarClientes() {
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
            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .border(border = BorderStroke(2.dp, Purple500), shape = RoundedCornerShape(15.dp))

            ) {

                Text(
                    text = "${clientes[index].nome}",
                    style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth() .padding(10.dp,10.dp)
                )
                Text(
                    text = "CPF: ${clientes[index].cpf}",
                    style = TextStyle(fontSize = 14.sp, ),
                    modifier = Modifier.fillMaxWidth() .padding(5.dp,0.dp)
                )
                Text(
                    text = "Telefone: ${clientes[index].telefone}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth() .padding(5.dp,0.dp)
                )
                Text(
                    text = "Endereço: ${clientes[index].endereco}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth() .padding(5.dp,0.dp)
                )
                Text(
                    text = "Instagram: ${clientes[index].instagram}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth() .padding(5.dp,0.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth() .padding(15.dp,10.dp),
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
                            //var passaCpf = clientes[index].cpf
                            //Toast.makeText(contexto, "Cliente selecionado: ${clientes[index].cpf}", Toast.LENGTH_SHORT).show()


                            var passaNome = clientes[index].nome
                            Toast.makeText(contexto, "Cliente selecionado: ${clientes[index].nome}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(contexto, TelaClienteComprar::class.java)
                            intent.putExtra("nome", passaNome)
                            contexto.startActivity(intent)
                            activity?.finish()},
                        modifier = Modifier.weight(1f)

                    ) {
                        Text("Comprar")
                    }

                }
                Spacer(modifier = Modifier.weight(1f, true))
            }
        }

    }
}



