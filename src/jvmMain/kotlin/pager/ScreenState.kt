package pager

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val items: SnapshotStateList<T> = mutableStateListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)