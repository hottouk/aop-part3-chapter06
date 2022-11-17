package com.example.aop_part3_chapter06.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part3_chapter06.R
import com.example.aop_part3_chapter06.model.Article
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(
    val articleList: MutableList<Article> = mutableListOf()
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_textview)
        val dateTextView: TextView = itemView.findViewById(R.id.date_textview)
        val priceTextView: TextView = itemView.findViewById(R.id.price_textview)
        val thumbNailImageView: ImageView = itemView.findViewById(R.id.thumbnail_imageview)

        fun bind(article: Article) {
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(article.createdAt)
            titleTextView.text = article.title
            dateTextView.text = format.format(date).toString()
            priceTextView.text = article.price
            if(article.imgUrl.isNotEmpty()){
                Glide.with(thumbNailImageView)
                    .load(article.imgUrl)
                    .into(thumbNailImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val articleView = inflater.inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(articleView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = (articleList[position])
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}