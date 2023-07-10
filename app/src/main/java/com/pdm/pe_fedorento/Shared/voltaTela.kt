package com.pdm.pe_fedorento.Shared

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun voltaTela() {
    val activity=(LocalContext.current as? Activity)
    Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth().width(200.dp)) {
        Text(text = "Voltar")
    }
}
