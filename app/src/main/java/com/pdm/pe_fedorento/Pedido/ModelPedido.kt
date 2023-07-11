package com.pdm.pe_fedorento.Pedido

import java.time.LocalDate

data class ModelPedido (val id_pedido: String,
                        val data :LocalDate,
                        val nomeCliente:String,
                        val id_produtos: List<String> = listOf())

