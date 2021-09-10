package co.tredo.newsapp2.data.remote

import co.tredo.newsapp2.data.remote.response.NewsResponse
import co.tredo.newsapp2.util.Hardcode.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsResponse>
}