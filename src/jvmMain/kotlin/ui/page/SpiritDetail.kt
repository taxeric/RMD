package ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import datasource.SpiritRemoteData
import entity.SpiritEntity
import net.Net
import ui.common.ExternalImage
import ui.common.horizontalDivider
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
fun spiritDetailImpl(
    data: SpiritEntity = SpiritEntity(),
    isAdd: Boolean = false,
    refresh: () -> Unit = {},
    commit: (SpiritEntity) -> Unit = {}
) {
    var avatar by remember(data.number) {
        mutableStateOf(data.avatar)
    }
    var number by remember(data.number) {
        mutableStateOf(data.number)
    }
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
    var power by remember(data.number) {
        mutableStateOf(data.racePower.toString())
    }
    var attack by remember(data.number) {
        mutableStateOf(data.raceAttack.toString())
    }
    var defense by remember(data.number) {
        mutableStateOf(data.raceDefense.toString())
    }
    var magicAttack by remember(data.number) {
        mutableStateOf(data.raceMagicAttack.toString())
    }
    var magicDefense by remember(data.number) {
        mutableStateOf(data.raceMagicDefense.toString())
    }
    var speed by remember(data.number) {
        mutableStateOf(data.raceSpeed.toString())
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.height(64.dp).align(Alignment.End)) {
            TextButton(onClick = refresh) {
                Text("refresh")
            }
            verticalSpacer()
            TextButton(onClick = {
                val entity = data.copy(avatar = avatar, number = number, name = name, hobby = hobby, description = desc,
                height = height.toDouble(), weight = weight.toDouble(),
                racePower = power.toInt(), raceAttack = attack.toInt(), raceDefense = defense.toInt(),
                raceSpeed = speed.toInt(), raceMagicAttack = magicAttack.toInt(), raceMagicDefense = magicDefense.toInt())
                commit(entity)
            }) {
                Text("commit data")
            }
        }
        horizontalDivider()
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            ExternalImage(Net.defaultImageClient, avatar, modifier = Modifier.size(100.dp).clip(RoundedCornerShape(5.dp)).background(
                Color.LightGray))
            if (isAdd) {
                OutlinedTextField(number, onValueChange = { number = it }, label = { Text("编号") })
            } else {
                Text(text = "编号: ${data.number}")
            }
            horizontalSpacer()
            OutlinedTextField(avatar, onValueChange = { avatar = it }, label = { Text("缩略图") })
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
            horizontalSpacer(5.dp)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(power, onValueChange = { power = it }, label = { Text("精力") }, modifier = Modifier
                    .width(100.dp))
                verticalSpacer()
                OutlinedTextField(attack, onValueChange = { attack = it }, label = { Text("攻击") }, modifier = Modifier
                    .width(100.dp))
                verticalSpacer()
                OutlinedTextField(defense, onValueChange = { defense = it }, label = { Text("防御") }, modifier = Modifier
                    .width(100.dp))
            }
            horizontalSpacer(5.dp)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(magicAttack, onValueChange = { magicAttack = it }, label = { Text("魔攻") }, modifier = Modifier
                    .width(100.dp))
                verticalSpacer()
                OutlinedTextField(magicDefense, onValueChange = { magicDefense = it }, label = { Text("魔抗") }, modifier = Modifier
                    .width(100.dp))
                verticalSpacer()
                OutlinedTextField(speed, onValueChange = { speed = it }, label = { Text("速度") }, modifier = Modifier
                    .width(100.dp))
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
