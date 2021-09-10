package co.tredo.newsapp2.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import co.tredo.newsapp2.util.Hardcode.DATABASE_NAME

abstract class NewsDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var instance: NewsDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}