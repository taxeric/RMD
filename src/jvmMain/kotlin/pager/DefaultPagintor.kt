package pager

/**
 * 默认实现类
 * 
 * @param initialKey 页数初始值
 * @param onCheckNoNewData 在每次获取下一页数据前检查是否已经没有新数据
 * @param onLoadUpdated 数据获取状态变化
 * @param onRequest 数据获取代码块
 * @param getNextKey 下一页数
 * @param onError 错误处理
 * @param onSuccess 成功处理
 */
class DefaultPagintor<Key, Item>(
    private val initialKey: Key,
    private inline val onCheckNoNewData: (Boolean) -> Boolean,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key, Boolean) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>, Boolean) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key, isRefresh: Boolean) -> Unit
): Pagintor {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(isRefresh: Boolean) {
        if (onCheckNoNewData(isRefresh)){
            return
        }
        if (isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey, isRefresh)
        isMakingRequest = false
        //设置值
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        //设置下一页
        currentKey = getNextKey(items, isRefresh)
        onSuccess(items, currentKey, isRefresh)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}