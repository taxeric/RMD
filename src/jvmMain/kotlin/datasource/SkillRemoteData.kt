package datasource

import androidx.compose.runtime.*
import baseUrl
import entity.Skill
import entity.SkillsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import log
import net.Net
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import skillPutUrl
import skillsUrl

class SkillRemoteData(private val scope: CoroutineScope? = null) {

    val skills = mutableStateListOf<Skill>()
    val commitResult = MutableStateFlow(500)
    private var isLoading = false
    private var isCommit = false

    fun commitSkill(skill: Skill){
        if (isCommit) {
            return
        }
//        skill.toString().log()
        scope?.run {
            launch {
                isCommit = true
                val requestBody = FormBody.Builder()
                    .add("name", skill.name)
                    .add("description", skill.description)
                    .add("value", skill.value.toString())
                    .add("amount", skill.amount.toString())
                    .add("speed", "0")
                    .add("isGenetic", "${skill.isGenetic}")
                    .add("additionalEffects", skill.additionalEffects)
                    .add("isBe", "${skill.isBe}")
                    .add("skillTypeId", "${skill.skillType.id}")
                    .add("attributesId", "${skill.attributes.id}")
                    .build()
                val request = Request.Builder()
                    .url("$baseUrl$skillPutUrl")
                    .post(requestBody)
                    .build()
                val response = Net.defaultClient.newCall(request).execute()
                commitResult.tryEmit(response.code)
                isCommit = false
            }
        }
    }

    suspend fun searchByName(keywords: String){
        skills.clear()
        if (isLoading) {
            return
        }
        "search -> $keywords".log()
        if (keywords.isEmpty()) {
            return
        }
        isLoading = true
        val request = Request.Builder()
            .url("$baseUrl$skillsUrl?page=1&amount=4&keywords=$keywords")
            .get()
            .build()
        val response = withContext(Dispatchers.Default) {
            Net.defaultClient.newCall(request).execute()
        }
        val entity = response.body?.run {
            Net.gson.fromJson(string(), SkillsList::class.java)
        }?: SkillsList()
        skills.addAll(entity.data)
        isLoading = false
    }
}