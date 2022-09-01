package pager

interface Pagintor {

    suspend fun loadNextItems(isRefresh: Boolean)
    fun reset()
}