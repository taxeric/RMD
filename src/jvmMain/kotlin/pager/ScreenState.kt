package pager

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val items: MutableList<T> = mutableListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)