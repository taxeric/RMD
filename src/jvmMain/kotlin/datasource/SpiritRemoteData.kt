package datasource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.cachedIn
import baseUrl
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import detailUrl
import entity.SpiritDetailEntity
import entity.SpiritEntity
import listUrl
import entity.SpiritList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import log
import net.Net
import net.NetResponse
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody

@OptIn(ExperimentalCoroutinesApi::class)
class SpiritRemoteData(scope: CoroutineScope) {

    var singleSpirit by mutableStateOf(SpiritDetailEntity())
    var chooseSpiritId by mutableStateOf(-1)
    private var isCommit = false
    val commitResult = MutableStateFlow(NetResponse(NetResponse.STATUS_IDLE))

    @OptIn(FlowPreview::class)
    val pager = Pager(
        initialKey = 1,
        config = PagingConfig(20, prefetchDistance = 1),
        clientScope = scope
    ){ currentKey, size ->
        val response = getSpiritData(currentKey)
        val items = response.data
        PagingResult(items = items, currentKey = currentKey, prevKey = {null}, nextKey = {if (response.data.isEmpty()) null else currentKey + 1})
    }.pagingData.cachedIn(scope)

    suspend fun getSpiritDataById(id: Int) {
        val request = Request.Builder()
            .url("$baseUrl$detailUrl?id=$id")
            .get()
            .build()
        val callBody = withContext(Dispatchers.Default) {
            Net.defaultClient.newCall(request).execute().body
        }
        val entity = callBody?.run {
            Net.gson.fromJson(this.string(), SpiritDetailEntity::class.java)
        }?: SpiritDetailEntity(code = 99)
        singleSpirit = entity
    }

    fun commitSpirit(spiritEntity: SpiritEntity, scope: CoroutineScope){
        spiritEntity.toString().log()
        if (isCommit) {
            return
        }
        isCommit = true
        commitResult.tryEmit(NetResponse(NetResponse.STATUS_LOADING))
        scope.run {
            launch {
                val requestBody = FormBody.Builder()
                    .add("id", spiritEntity.id.toString())
                    .add("avatar", spiritEntity.avatar)
                    .add("number", spiritEntity.number)
                    .add("description", spiritEntity.description)
                    .add("racePower", spiritEntity.racePower.toString())
                    .add("raceAttack", spiritEntity.raceAttack.toString())
                    .add("raceDefense", spiritEntity.raceDefense.toString())
                    .add("raceMagicAttack", spiritEntity.raceMagicAttack.toString())
                    .add("raceMagicDefense", spiritEntity.raceMagicDefense.toString())
                    .add("raceSpeed", spiritEntity.raceSpeed.toString())
                    .add("height", spiritEntity.height.toString())
                    .add("weight", spiritEntity.weight.toString())
                    .add("hobby", spiritEntity.hobby)
                    .add("group", spiritEntity.group.id.toString())
                    .add("primaryAttributes", spiritEntity.primaryAttributes.id.toString())
                    .add("skills", "null")
                    .build()
                val request = Request.Builder()
                    .url("$baseUrl$detailUrl")
                    .put(requestBody)
                    .build()
                val response = Net.defaultClient.newCall(request).execute()
                commitResult.tryEmit(NetResponse(NetResponse.STATUS_COMPLETED, response.isSuccessful))
                isCommit = false
                delay(1000)
                commitResult.tryEmit(NetResponse(NetResponse.STATUS_IDLE))
            }
        }
    }

    private suspend fun getSpiritData(page: Int): SpiritList {
        val request = Request.Builder()
            .url("$baseUrl$listUrl?page=$page&amount=20&keywords=")
            .get()
            .build()
        val callBody = withContext(Dispatchers.Default) {
            Net.defaultClient.newCall(request).execute().body
        }
        val entity = callBody?.run {
            Net.gson.fromJson(this.string(), SpiritList::class.java)
        }?: SpiritList(code = 99)
        return entity
    }
}