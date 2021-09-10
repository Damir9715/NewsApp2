package co.tredo.newsapp2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import co.tredo.newsapp2.presentation.bookmarks.BookmarksFragment
import co.tredo.newsapp2.presentation.news.NewsFragment
import co.tredo.newsapp2.presentation.search.SearchFragment
import co.tredo.newsapp2.util.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//todo use navigation component or simple-stack for support multiple backstack
//todo bottomNav ic_bookmark should be replaced by ic_filled_bookmark
//todo save news on database for offline usage, RemoteMediator
//todo use di framework
//todo format publishedAt date
//todo use kotlin serialization for default value for nullable fields
//todo detect internet connectedEvent, add retry button
//todo refresh loaded news each 5 seconds
//todo refresh loaded news by swipe
//todo save favourite news on database
class MainActivity : AppCompatActivity(R.layout.activity_main), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment<NewsFragment>(addToBackStack = false)
        }

        findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.app_name)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newsItem -> replaceFragment<NewsFragment>()
                R.id.searchItem -> replaceFragment<SearchFragment>()
                R.id.bookmarksItem -> replaceFragment<BookmarksFragment>()
            }
            true
        }
    }

    override suspend fun onPayload(payload: Router.Payload) = withContext(Dispatchers.Main) {
        //todo
    }
}