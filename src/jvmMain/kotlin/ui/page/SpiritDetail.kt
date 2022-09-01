package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import datasource.SpiritRemoteData
import entity.SpiritEntity
import ui.common.horizontalSpacer
import ui.common.verticalSpacer

@Composable
fun spiritDetailScreen(remoteData: SpiritRemoteData) {
    val id = remoteData.chooseSpiritId
    if (id == -1) {
        spiritEmptyData()
    } else {
        spiritDetailNet(id, remoteData)
    }
}

@Composable
fun spiritDetailNet(id: Int, remoteData: SpiritRemoteData) {
    val entity = remoteData.singleSpirit
    LaunchedEffect(key1 = "getDetail", key2 = id) {
        remoteData.getSpiritDataById(id)
    }
    if (entity.code == 200) {
        spiritDetailImpl(entity.data)
    } else {
        Text(text = "出错了 ${entity.msg}")
    }
}

@Composable
fun spiritDetailImpl(data: SpiritEntity) {
    var name by remember(data.number) {
        mutableStateOf(data.name)
    }
    var hobby by remember(data.number) {
        mutableStateOf(data.hobby)
    }
    var desc by remember(data.number) {
        mutableStateOf(data.description)
    }
    var height by remember(data.number) {
        mutableStateOf(data.height.toString())
    }
    var weight by remember(data.number) {
        mutableStateOf(data.weight.toString())
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.height(64.dp).align(Alignment.End)) {
            TextButton(onClick = {
            }) {
                Text("refresh")
            }
            verticalSpacer()
            TextButton(onClick = {
            }) {
                Text("commit data")
            }
        }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Text(text = "编号: ${data.number}")
            horizontalSpacer()
            OutlinedTextField(name, onValueChange = { name = it }, label = { Text("精灵名") })
            horizontalSpacer(5.dp)
            OutlinedTextField(hobby, onValueChange = { hobby = it }, label = { Text("爱好") })
            horizontalSpacer(5.dp)
            OutlinedTextField(desc, onValueChange = { desc = it }, label = { Text("简介") })
            horizontalSpacer(5.dp)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(height, onValueChange = { height = it }, label = { Text("身高") }, modifier = Modifier
                    .width(100.dp))
                Text("m")
                verticalSpacer()
                OutlinedTextField(weight, onValueChange = { weight = it }, label = { Text("体重") }, modifier = Modifier
                    .width(100.dp))
                Text("kg")
            }
        }
    }
}

@Composable
fun spiritEmptyData(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "请选择精灵", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxSize())
    }
}
