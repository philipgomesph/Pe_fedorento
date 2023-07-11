package com.pdm.pe_fedorento.Pedido

data class ModelPedido(
    val id_pedido: String?,
    val dia: String,
    val nomeCliente:String?,
    val id_produtos: List<String>? = listOf())

