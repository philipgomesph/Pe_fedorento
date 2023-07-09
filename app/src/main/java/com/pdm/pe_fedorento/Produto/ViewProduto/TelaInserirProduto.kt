package com.pdm.pe_fedorento.Produto.ViewProduto

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Produto.ViewProduto.ui.theme.Pe_fedorentoTheme
import com.pdm.pe_fedorento.Shared.limparCampos

class TelaInserirProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            Pe_fedorentoTheme {

                insereProduto()
            }
        }
    }
}

@Composable
fun insereProduto(){
    val contexto = LocalContext.current
    val db = FirebaseFirestore.getInstance()


    val idProdutoTF = remember{ mutableStateOf(TextFieldValue()) }
    val descricaoTF = remember{ mutableStateOf(TextFieldValue()) }
    val valorTF = remember{ mutableStateOf(TextFieldValue()) }
    val fotoTF = remember{ mutableStateOf(TextFieldValue()) }

    val activity=(LocalContext.current as? Activity)




    Column(
        Modifier.padding(40.dp)
    ) {
        Text(text = "Inserir Produto", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        TextField(
            value =idProdutoTF.value ,
            onValueChange ={idProdutoTF.value = it},
            placeholder = { Text(text = "ID Produto")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =descricaoTF.value ,
            onValueChange ={descricaoTF.value = it},
            placeholder = { Text(text = "Descrição do produto")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =valorTF.value ,
            onValueChange ={valorTF.value = it},
            placeholder = { Text(text = "Valor")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =fotoTF.value ,
            onValueChange ={fotoTF.value = it},
            placeholder = { Text(text = "Foto")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())


        Button(
            onClick = {

                val produto = hashMapOf(
                    "id" to idProdutoTF.value.text,
                    "descricao" to descricaoTF.value.text,
                    "valor" to valorTF.value.text.toDouble(),
                    "foto" to fotoTF.value.text,
                )

                db.collection("produto").document(idProdutoTF.value.text).set(produto)
                    .addOnSuccessListener {
                        Toast.makeText(contexto, "Inserção realizada com sucesso", Toast.LENGTH_LONG).show()

                    }
                    .addOnFailureListener {
                        Toast.makeText(contexto, "Inserção não realizada", Toast.LENGTH_LONG).show()
                    }

                limparCampos(idProdutoTF, descricaoTF, valorTF, fotoTF)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Inserir")

        }
        Spacer(modifier = Modifier.weight(1f, true))
        Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }
    }
}
