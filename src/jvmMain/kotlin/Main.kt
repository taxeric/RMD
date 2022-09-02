// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import datasource.SpiritRemoteData
import ui.common.verticalDivider
import ui.page.skillScreen
import ui.page.spiritDetailScreen
import ui.page.spiritScreen

@Composable
fun App() {

    val scope = rememberCoroutineScope()
    val remoteData = SpiritRemoteData(scope)

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(3f)) {
                spiritScreen({
                    remoteData.chooseSpiritId = it
                }, remoteData)
            }
            verticalDivider()
            Column(modifier = Modifier.weight(7f).padding(10.dp)) {
                spiritDetailScreen(remoteData)
            }
        }
    }
}

fun main() = application {
    var clickAddSkill by remember {
        mutableStateOf(false)
    }
    Window(onCloseRequest = ::exitApplication, title = "RMD") {
        MenuBar {
            Menu("other") {
                Item("add skill") {
                    clickAddSkill = true
                }
            }
        }
        App()
    }
    if (clickAddSkill) {
        windowAddSkill {
            clickAddSkill = false
        }
    }
}

@Composable
fun windowAddSkill(onDismiss: () -> Unit){
    Window(onCloseRequest = onDismiss, title = "add skill"){
        skillScreen()
    }
}
