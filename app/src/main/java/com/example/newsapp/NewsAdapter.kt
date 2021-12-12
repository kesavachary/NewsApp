package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context,val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewModel>() {
    //Adapter is generic type must type pass and implement three members
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewModel {
        //create view for display item
        val view=LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        //return the view
        return ArticleViewModel(view)
    }
    //in this method data binding according position
    override fun onBindViewHolder(holder: ArticleViewModel, position: Int) {
        //call article position and done data binding
        val article=articles[position]
        //call article.title and store in newsTitle
        holder.newsTitle.text=article.title
        //call article.desc and store in newsDescription
        holder.newsDescription.text=article.description
        //for using Glide library Images loading before use we need to add dependencies
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        //adding setonClickListener display Toast Message
        holder.itemView.setOnClickListener{
            //display the Toast message Title whenever click
            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()
            //after click particular item it redirects the DetailActivity
            val intent=Intent(context,DetailActivity::class.java)
            //open the URl clicked article item
            intent.putExtra("URL",article.url)
            //and start the activity intent(intention) that is Open DetailActivity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //return articles size
       return articles.size
    }
    //ArticleViewModel class inherit the RecycleView.ViewModel and store the Views References
    class ArticleViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage = itemView.findViewById<ImageView>(R.id.newsImgge)
        var newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        var newsDescription = itemView.findViewById<TextView>(R.id.newDescription)
    }
}