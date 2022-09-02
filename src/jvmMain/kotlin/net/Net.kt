package net

import com.google.gson.Gson
import okhttp3.OkHttpClient

object Net {

    val gson = Gson()

    val defaultClient = OkHttpClient.Builder()
        .addInterceptor(PrintInterceptor())
        .build()

    val defaultImageClient = OkHttpClient.Builder()
        .build()
}