package com.example.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Models.NewsApiResponse
import com.example.newsapp.Models.NewsHeadlines
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NewsApiAdapter(
    private val context: Context,
    private val headlines: List<NewsHeadlines>,
    private val listener: SelectListener

) : RecyclerView.Adapter<MainActivity.CustomViewHolder>() {

    override fun getItemCount(): Int {
        return headlines.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivity.CustomViewHolder {
        return MainActivity.CustomViewHolder(
            LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainActivity.CustomViewHolder, position: Int) {
        holder.text_title.text = headlines[position].title
        holder.text_source.text = headlines[position].source!!.name
        if (headlines[position].urlToImage != null) {
            Picasso.get().load(headlines[position].urlToImage).into(holder.img_headline)
        }
        holder.cardView.setOnClickListener {
            listener.ClickedNewsStory(
                headlines[position]
            )
        }
    }

    interface GET_News_API {
        @GET("top-headlines")
        fun callHeadlines(
            @Query("country") country: String?,
            @Query("category") category: String?,
            @Query("q") query: String?,
            @Query("apiKey") api_key: String?
        ): Call<NewsApiResponse>
    }
    class LoadingNewsStories(var context: Context) {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun LoadNewsArticleTitle(listener: OnFetchDataListener<*>, category: String?, query: String?) {
            val GET_News_API = retrofit.create(GET_News_API::class.java)
            // ADD country choices in an array list
            val call =
                GET_News_API.callHeadlines("gb", category, query, context.getString(R.string.api_key))
            try {
                call.enqueue(object : Callback<NewsApiResponse> {
                    override fun onResponse(
                        call: Call<NewsApiResponse>,
                        response: Response<NewsApiResponse>
                    ) {
                        if (!response.isSuccessful) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                        listener.onFetchData(response.body()!!.articles, response.message())
                    }

                    override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                        listener.onError("Request Failed")
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}

