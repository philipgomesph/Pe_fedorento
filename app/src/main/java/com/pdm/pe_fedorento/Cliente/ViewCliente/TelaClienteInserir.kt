package com.pdm.pe_fedorento.Cliente.ViewCliente

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.pdm.pe_fedorento.Shared.limparCampos

class TelaClienteInserir (): ComponentActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent{
            Insercao()
        }
    }
}


@Composable
fun Insercao(){
    val contexto = LocalContext.current
    val db = FirebaseFirestore.getInstance()


    val cpfTextField = remember{ mutableStateOf(TextFieldValue()) }
    val nomeTextField = remember{ mutableStateOf(TextFieldValue()) }
    val telefoneTextField = remember{ mutableStateOf(TextFieldValue()) }
    val enderecoTextField = remember{ mutableStateOf(TextFieldValue()) }
    val instagramTextField = remember{ mutableStateOf(TextFieldValue()) }
    val activity=(LocalContext.current as? Activity)




    Column(
        Modifier.padding(40.dp)
    ) {
        Text(text = "Inserir Cliente", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        TextField(
            value =cpfTextField.value ,
            onValueChange ={cpfTextField.value = it},
            placeholder = { Text(text = "CPF")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =nomeTextField.value ,
            onValueChange ={nomeTextField.value = it},
            placeholder = { Text(text = "Nome do Cliente")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =telefoneTextField.value ,
            onValueChange ={telefoneTextField.value = it},
            placeholder = { Text(text = "Telefone")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =enderecoTextField.value ,
            onValueChange ={enderecoTextField.value = it},
            placeholder = { Text(text = "Endereço")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value =instagramTextField.value ,
            onValueChange ={instagramTextField.value = it},
            placeholder = { Text(text = "@Instagram")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth())



        Button(
            onClick = {

                val cliente = hashMapOf(
                    "cpf" to cpfTextField.value.text,
                    "nome" to nomeTextField.value.text,
                    "telefone" to telefoneTextField.value.text,
                    "endereco" to enderecoTextField.value.text,
                    "instagram" to instagramTextField.value.text
                )

                db.collection("cliente").document(nomeTextField.value.text).set(cliente)
                    .addOnSuccessListener {
                        Toast.makeText(contexto, "Inserção realizada com sucesso", Toast.LENGTH_LONG).show()
                        limparCampos(cpfTextField, nomeTextField, telefoneTextField, enderecoTextField, instagramTextField)
                    }
                    .addOnFailureListener {
                        Toast.makeText(contexto, "Inserção não realizada", Toast.LENGTH_LONG).show()
                    }

                limparCampos(cpfTextField, nomeTextField, telefoneTextField, enderecoTextField, instagramTextField)

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


