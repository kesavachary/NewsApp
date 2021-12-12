package com.example.newsapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    //create object adapter inherit NewAdapter
    lateinit var adapter: NewsAdapter
    private val articles = mutableListOf<Article>()
    //page number assign 1 initial
    var pageNum=1
    //totalResults -1 initialize
    var totalResults=-1
    //TAG initialize MainActivity
    val TAG="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pass context MainActivity and list of List<articles>
        adapter = NewsAdapter(this@MainActivity, articles)
        //assign the adapter type newsList
        newsList.adapter = adapter
        //  newsList.layoutManager=LinearLayoutManager(this@MainActivity)
        //layoutmanager for using news display type ScrollOrientation BOTTOM_TO_TOP,etc
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        //set velocity (2000ms)
        layoutManager.setPagerFlingVelocity(2000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                //using container(activity_main id) for using different bg colors
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG,"First Visible Item - ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total Count -${layoutManager.itemCount}")
                //if totalResults greater then layoutManager itemCount &&layoutManager getfirstVisiblePosition greater then itemCount-5
                if(totalResults>layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition()>=layoutManager.itemCount -5){
                    //page number increment as 2
                    pageNum++
                    getNews()
                }
            }

        })
        newsList.layoutManager = layoutManager
        getNews()
    }

    private fun getNews() {
        Log.d(TAG,"Request sent for $pageNum")
        //create news instance and call newservice.newInstance(singlton object).getHeadlines pass 'in'& pageNum
        val news = NewService.newsInstance.getHeadlines("in", pageNum)
        //in retrofit process done one by one in Queue process and use Callback method for News
        news.enqueue(object : Callback<News> {
            //two implemented methods that is onResponse & onFailure
            override fun onFailure(call: Call<News>, t: Throwable) {
                //log msg and exception object(t)
                Log.d("newsapp", "Error in Fetching data", t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                //body response news type
                val news = response.body()
                //check if news not null then
                if (news != null) {
                    totalResults=news.totalResult
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }
}
