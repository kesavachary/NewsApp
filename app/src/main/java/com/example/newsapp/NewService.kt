package com.example.newsapp

import android.telecom.Call
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/everything?q=tesla&from=2021-11-09&sortBy=publishedAt&apiKey=API_KEY
//https://newsapi.org/v2/top-headlines?country=in&apiKey=631096d241f64ae48e776c448589a320
const val BASE_URL="https://newsapi.org/" //constant base URL
const val API_KEY="631096d241f64ae48e776c448589a320"  // declare constant NEWSAPI KEY
interface  NewsInterface{
    @GET("v2/top-headlines?apiKey=$API_KEY")  //used to access data for a specific resource from a REST API
    fun getHeadlines(@Query("country")country:String,@Query("page")page:Int):retrofit2.Call<News>//callback method success or failed
    //function used to get the headlines of the country according to the query
    //https://newsapi.org/v2/top-headlines?apiKey=$API_KEY&country=in&page=1
}
//NewsService is the instance of retrofit made by using Singleton object
object NewService {
    val newsInstance:NewsInterface
    init {

        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance=retrofit.create(NewsInterface::class.java)
    }
}