// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import datasource.SpiritRemoteData

@Composable
fun App() {

    val scope = rememberCoroutineScope()
    val remoteData = SpiritRemoteData(scope)

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(3f)) {
                spiritScreen({
                    "点击了 $it".log()
                }, remoteData)
            }
            Column(modifier = Modifier.weight(7f)) {
                SpiritDetailScreen()
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
