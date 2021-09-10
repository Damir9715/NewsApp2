package co.tredo.newsapp2.data.remote

import co.tredo.newsapp2.util.Hardcode.API_KEY
import co.tredo.newsapp2.util.Hardcode.BASE_URL
import co.tredo.newsapp2.util.Hardcode.CONNECT_TIMEOUT_IN_SECONDS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(API_KEY))
            .connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val newsApi: NewsApi by lazy { retrofit.create(NewsApi::class.java) }
}