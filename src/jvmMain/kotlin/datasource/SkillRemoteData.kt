package datasource

import androidx.compose.runtime.*
import baseUrl
import entity.Skill
import entity.SkillsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import log
import net.Net
import okhttp3.Request
import skillsUrl

class SkillRemoteData() {

    val skills = mutableStateListOf<Skill>()
    private var isLoading = false

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