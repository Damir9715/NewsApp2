package co.tredo.newsapp2.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.tredo.newsapp2.data.remote.response.Article
import kotlinx.coroutines.flow.Flow

interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("select * from article")
    fun getArticles(): Flow<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}