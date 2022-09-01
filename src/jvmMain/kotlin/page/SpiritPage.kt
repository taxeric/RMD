import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import entity.SpiritEntity

@Composable
fun SpiritList(list: List<SpiritEntity>, onClick: (Int) -> Unit){
    LazyColumn {
        itemsIndexed(list) { index, data ->
            SpiritItem(data, onClick)
        }
    }
}

@Composable
fun SpiritItem(data: SpiritEntity, onClick: (Int) -> Unit){
    Text(text = "${data.number} ${data.name}", modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(data.id) }
        .padding(10.dp))
}
