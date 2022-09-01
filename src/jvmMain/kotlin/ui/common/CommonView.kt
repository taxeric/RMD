package ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
