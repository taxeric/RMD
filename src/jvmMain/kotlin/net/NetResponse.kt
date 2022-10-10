package net

data class NetResponse(
    val code: Int,
    val success: Boolean = false
) {
    companion object {
        const val STATUS_IDLE = 0
        const val STATUS_LOADING = 1
        const val STATUS_COMPLETED = 2
    }
}
