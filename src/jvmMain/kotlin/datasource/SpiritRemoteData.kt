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
import net.Net
import okhttp3.Request

@OptIn(ExperimentalCoroutinesApi::class)
class SpiritRemoteData(scope: CoroutineScope) {

    var singleSpirit by mutableStateOf(SpiritDetailEntity())
    var chooseSpiritId by mutableStateOf(-1)

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