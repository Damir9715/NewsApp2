package co.tredo.newsapp2.data.remote

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_HEADER = "X-Api-Key"

class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(API_KEY_HEADER, apiKey)
            .build()

        return chain.proceed(request)
    }
}