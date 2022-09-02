package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cache.LocalCache
import datasource.SkillRemoteData
import entity.Skill
import entity.SkillType
import entity.SpiritAttributes
import ui.common.verticalSpacer

@Composable
fun skillScreen(){
    val remoteData = SkillRemoteData(rememberCoroutineScope())
    var name by remember {
        mutableStateOf("")
    }
    var desc by remember {
        mutableStateOf("")
    }
    var additionalEffect by remember {
        mutableStateOf("-")
    }
    var isBe by remember {
        mutableStateOf(false)
    }
    var isGenetic by remember {
        mutableStateOf(false)
    }
    var isSpeed by remember {
        mutableStateOf(false)
    }
    var value by remember {
        mutableStateOf("0")
    }
    var maxPP by remember {
        mutableStateOf("0")
    }
    var skillType by remember {
        mutableStateOf(SkillType(1, "物理"))
    }
    var attr by remember {
        mutableStateOf(SpiritAttributes(1, "冰系"))
    }
    var m1drop by remember {
        mutableStateOf(false)
    }
    var m2drop by remember {
        mutableStateOf(false)
    }
    var commit by remember {
        mutableStateOf(false)
    }
    val result = remoteData.commitResult.collectAsState().value
    Column(modifier = Modifier.fillMaxSize()){
        OutlinedTextField(name, onValueChange = { name = it }, label = { Text("技能名") })
        OutlinedTextField(desc, onValueChange = { desc = it }, label = { Text("描述") })
        OutlinedTextField(additionalEffect, onValueChange = { additionalEffect = it }, label = { Text("附带效果") })
        Row(modifier = Modifier.fillMaxWidth().height(48.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("必中?")
            Checkbox(isBe, onCheckedChange = { isBe = it })
            verticalSpacer()
            Text("遗传?")
            Checkbox(isGenetic, onCheckedChange = { isGenetic = it })
            verticalSpacer()
            Text("先手?")
            Checkbox(isSpeed, onCheckedChange = { isSpeed = it })
        }
        OutlinedTextField(maxPP, onValueChange = { maxPP = it }, label = { Text("最大PP") })
        OutlinedTextField(value, onValueChange = { value = it }, label = { Text("伤害值") })
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(attr.name!!, color = Color.Blue)
            TextButton(onClick = { m1drop = true }){
                Text("变更技能属性")
            }
            DropdownMenu(m1drop, onDismissRequest = { m1drop = false }) {
                LocalCache.attrs.forEachIndexed { index, spiritAttributes ->
                    DropdownMenuItem(onClick = {
                        m1drop = false
                        attr = attr.copy(id = spiritAttributes.id, name = spiritAttributes.name)
                    }) {
                        Text(spiritAttributes.name!!)
                    }
                }
            }
            verticalSpacer()
            Text(skillType.name, color = Color.Magenta)
            TextButton(onClick = { m2drop = true }){
                Text("变更技能类型")
            }
            DropdownMenu(m2drop, onDismissRequest = { m2drop = false }) {
                LocalCache.skillType.forEachIndexed { index, type ->
                    DropdownMenuItem(onClick = {
                        m2drop = false
                        skillType = skillType.copy(id = type.id, name = type.name)
                    }) {
                        Text(type.name)
                    }
                }
            }
        }
        Button(onClick = { commit = true }) {
            Text("提交")
        }
        if (result == 200) {
            Text("success $name")
        }
    }
    if (commit) {
        LaunchedEffect(key1 = Unit) {
            val skill = Skill(
                name = name,
                description = desc,
                additionalEffects = additionalEffect,
                isBe = isBe,
                isGenetic = isGenetic,
                amount = maxPP.toInt(),
                value = value.toInt(),
                attributes = attr,
                skillType = skillType
            )
            remoteData.commitSkill(skill)
        }
    }
}
