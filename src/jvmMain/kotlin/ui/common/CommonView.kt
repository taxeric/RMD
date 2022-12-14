package ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.skia.Image

@Composable
fun verticalDivider(width: Dp = 1.dp, color: Color = Color.Gray){
    Divider(modifier = Modifier.width(width).fillMaxHeight(), color = color)
}

@Composable
fun horizontalDivider(height: Dp = 1.dp, color: Color = Color.Gray){
    Divider(modifier = Modifier.height(height).fillMaxWidth(), color = color)
}

@Composable
fun verticalSpacer(width: Dp = 10.dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun horizontalSpacer(height: Dp = 10.dp) {
    Spacer(modifier = Modifier.height(height))
}

suspend fun OkHttpClient.loadPic(url: String): Result<ImageBitmap> {
    if (url.isEmpty()) return Result.failure(NullPointerException("empty url"))
    val request = Request.Builder()
        .get()
        .url(url)
        .build()
    return try {
        val response = withContext(Dispatchers.Default) {
            newCall(request).execute()
        }
        val byteArray = withContext(Dispatchers.Main) {
            response.body?.bytes()
        }
        Result.success(Image.makeFromEncoded(byteArray).toComposeImageBitmap())
    } catch (e: Exception) {
        "failed -> ${e.message}".log()
        Result.failure(e)
    }
}

@Composable
fun ExternalImage(client: OkHttpClient, url: String, modifier: Modifier = Modifier, content: String? = null){
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isFailed by remember {
        mutableStateOf(false)
    }
    var failedStr by remember(url) {
        mutableStateOf("")
    }
    var imageBitmap: ImageBitmap? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(key1 = url) {
        isLoading = true
        isFailed = false
        client.loadPic(url)
            .onSuccess {
                imageBitmap = it
            }
            .onFailure {
                it.printStackTrace()
                failedStr = it.message?:"unknow"
                isFailed = true
            }
        isLoading = false
    }
    when {
        isLoading -> {
            Column(modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
        isFailed -> {
            Text("?????? -> $failedStr", color = Color.Red, modifier = Modifier.padding(10.dp))
        }
        else -> {
            imageBitmap?.let {
                Image(it, contentDescription = content, modifier = modifier)
            }
        }
    }
}


/**
 * ????????????,??????????????????view???width?????????match_parent
 */
@Composable
fun flowLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit){
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        var maxHeight = 0
        var appendWidth = 0
        val placeableView = mutableListOf<Placeable>()
        measurables.forEachIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            placeableView.add(placeable)
            val childHeight = placeable.height
            val childWidth = placeable.width
            if (childWidth + appendWidth > constraints.maxWidth) {
                maxHeight += childHeight
                appendWidth = childWidth
            } else {
                appendWidth += childWidth
            }
            if (index == measurables.size - 1) {
                maxHeight += childHeight
            }
        }

        var layoutLeft = 0
        var singleHeight = 0
        layout(constraints.maxWidth, maxHeight) {
            placeableView.forEachIndexed { index, childView ->
                if (layoutLeft + childView.width > constraints.maxWidth) {
                    singleHeight += childView.height
                    layoutLeft = 0
                }
                childView.place(x = 0, y = 0)
                layoutLeft += childView.width
            }
        }
    }
}
