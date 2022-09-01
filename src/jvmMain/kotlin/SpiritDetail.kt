import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SpiritDetailScreen(id: Int = -1) {
    if (id == -1) {
        SpiritEmptyData()
    } else {
        SpiritDetailImpl(id)
    }
}

@Composable
fun SpiritDetailImpl(id: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "$id")
    }
}

@Composable
fun SpiritEmptyData(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "请选择精灵", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxSize())
    }
}
