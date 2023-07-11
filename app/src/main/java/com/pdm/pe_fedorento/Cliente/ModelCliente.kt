package com.pdm.pe_fedorento.Cliente

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

data class ModelCliente (val cpf: String,
                         val nome: String,
                         val telefone: String,
                         val endereco: String,
                         val instagram: String,
                         val id_pedido: List<String>? = listOf()
                         )

