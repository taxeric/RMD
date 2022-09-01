import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import datasource.SpiritRemoteData
import entity.SpiritEntity
import pager.LazyPagingItems
import pager.collectAsLazyPagingItems
import pager.lazyItemsIndexed

@Composable
fun spiritScreen(onClick: (Int) -> Unit, remoteData: SpiritRemoteData){
    val list = remoteData.pager.collectAsLazyPagingItems()
    val scrollState = rememberLazyListState()
    spiritListImpl(list, onClick, scrollState)
}

@Composable
fun spiritListImpl(list: LazyPagingItems<SpiritEntity>, onClick: (Int) -> Unit, scrollState: LazyListState){
    LazyColumn(modifier = Modifier.fillMaxSize(), state = scrollState) {
        lazyItemsIndexed(list) {index, data ->
            spiritItem(data!!, onClick)
        }
    }
}

@Composable
fun spiritItem(data: SpiritEntity, onClick: (Int) -> Unit){
    Text(text = "${data.number} ${data.name}", modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(data.id) }
        .padding(10.dp))
}
