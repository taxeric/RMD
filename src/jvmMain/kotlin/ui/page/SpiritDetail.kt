package ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import datasource.SpiritRemoteData
import entity.SpiritEntity

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
    var name by remember {
        mutableStateOf(data.name)
    }
    var hobby by remember {
        mutableStateOf(data.hobby)
    }
    var desc by remember {
        mutableStateOf(data.description)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = data.number)
        OutlinedTextField(name, onValueChange = { name = it }, label = { Text("精灵名") })
        OutlinedTextField(hobby, onValueChange = { hobby = it }, label = { Text("爱好") })
        OutlinedTextField(desc, onValueChange = { desc = it }, label = { Text("简介") })
    }
}

@Composable
fun spiritEmptyData(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "请选择精灵", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxSize())
    }
}
