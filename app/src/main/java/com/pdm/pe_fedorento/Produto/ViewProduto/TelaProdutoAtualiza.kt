package com.pdm.pe_fedorento.Produto.ViewProduto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.pdm.pe_fedorento.Cliente.ViewCliente.UpdateCliente

class TelaProdutoAtualiza : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        val idProduto = intent.getStringExtra("id")


        setContent {
            Toast.makeText(this, "Nome que veio: $idProduto", Toast.LENGTH_LONG).show()
            if (idProduto != null) {
                UpdateCliente(idProduto)
            }
        }
    }
}

