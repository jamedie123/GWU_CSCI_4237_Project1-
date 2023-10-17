package com.example.newsapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Models.NewsApiResponse
import com.example.newsapp.Models.NewsHeadlines
import com.squareup.picasso.Picasso

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//import com.example.mygooglemap.databinding.ActivityMyGoogleMapBinding
//import java.util.List;

class MainActivity : AppCompatActivity(), SelectListener, View.OnClickListener {
    private lateinit var  recyclerView: RecyclerView
    lateinit var  adapter: NewsApiAdapter
    lateinit var  dialog: ProgressDialog
    lateinit var  BUTTON1: Button
    lateinit var  BUTTON2: Button
    lateinit var  BUTTON3: Button
    lateinit var  BUTTON4: Button
    lateinit var  BUTTON5: Button
    lateinit var  BUTTON6: Button
    lateinit var  BUTTON7: Button
    lateinit var  BUTTON8: Button
    lateinit var  searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val actionBar = supportActionBar
//        actionBar!!.title = "Home Page"

        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                dialog.setTitle("Loading current News Articles $ ")
                dialog.show()
                val manager = NewsApiAdapter.LoadingNewsStories(this@MainActivity)
                manager.LoadNewsArticleTitle(listener, "general", query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        dialog = ProgressDialog(this)
        dialog.setTitle("Loading current News Articles ")
        dialog.show()
        BUTTON1 = findViewById(R.id.BUTTON_1)
        BUTTON2 = findViewById(R.id.BUTTON_2)
        BUTTON3 = findViewById(R.id.BUTTON_3)
        BUTTON4 = findViewById(R.id.BUTTON_4)
        BUTTON5 = findViewById(R.id.BUTTON_5)
        BUTTON6 = findViewById(R.id.BUTTON_6)
        BUTTON7 = findViewById(R.id.BUTTON_7)
        BUTTON8 = findViewById(R.id.BUTTON_8)
        BUTTON1.setOnClickListener(this)
        BUTTON2.setOnClickListener(this)
        BUTTON3.setOnClickListener(this)
        BUTTON4.setOnClickListener(this)
        BUTTON5.setOnClickListener(this)
        BUTTON6.setOnClickListener(this)
        BUTTON7.setOnClickListener(this)
        BUTTON8.setOnClickListener(this)
        val manager = NewsApiAdapter.LoadingNewsStories(this)
        manager.LoadNewsArticleTitle(listener, "general", null)

        BUTTON8.setOnClickListener {
//            fun onClick(v: View) {
//                val intent = Intent(this, MapsActivity::class.java)
//                startActivity(intent)
//            }

             fun onClick(v: View) {
                val button = v as Button
                val category = button.text.toString()
                dialog.setTitle("Loading current News Articles $category")
                dialog.show()
                val manager = NewsApiAdapter.LoadingNewsStories(this)
                 manager.LoadNewsArticleTitle(listener, category, null)
                 val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private val listener: OnFetchDataListener<NewsApiResponse?> =
        object : OnFetchDataListener<NewsApiResponse?> {
            override fun onFetchData(list: List<NewsHeadlines>, message: String) {
                if (list.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Story Currently Unavailable. ", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    DisplayNewsStory(list)
                    dialog!!.dismiss()
                }
            }

            override fun onError(message: String) {
                Toast.makeText(this@MainActivity, "Please Reload the page. ", Toast.LENGTH_SHORT).show()
            }
        }

    private fun DisplayNewsStory(list: List<NewsHeadlines>) {
        recyclerView = findViewById(R.id.recycler_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))
        adapter = NewsApiAdapter(this, list, this)
        recyclerView.setAdapter(adapter)
    }

    override fun ClickedNewsStory(headlines: NewsHeadlines) {
//        override fun ClickedNewsStory(headlines: NewsHeadlines) {
        startActivity(
            Intent(this@MainActivity, DetailsActivity::class.java)
                .putExtra("data", headlines)
        )
    }

    override fun onClick(v: View) {
        val button = v as Button
        val category = button.text.toString()
        dialog.setTitle("Loading current News Articles $category")
        dialog.show()
        val manager = NewsApiAdapter.LoadingNewsStories(this)
        manager.LoadNewsArticleTitle(listener, category, null)
    }
    // Add 2nd function for Button8 connect to Map //

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text_title: TextView
        var text_source: TextView
        var img_headline: ImageView
        var cardView: CardView

        init {
            text_title = itemView.findViewById(R.id.text_title)
            text_source = itemView.findViewById(R.id.text_source)
            img_headline = itemView.findViewById(R.id.img_headline)
            cardView = itemView.findViewById(R.id.main_container)
        }
    }

    class DetailsActivity : AppCompatActivity() {
        private var HeadLines: NewsHeadlines = intent.getSerializableExtra("data") as NewsHeadlines
        private var StoryImage: ImageView = findViewById(R.id.img_news)
        private var TextContent: TextView = findViewById(R.id.text_content)
        private var TextAuthor: TextView = findViewById(R.id.text_author)
        private var TextTime: TextView = findViewById(R.id.text_time)
        private var TextTitle: TextView = findViewById(R.id.text_title)
        private var TextDetail: TextView = findViewById(R.id.text_detail)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_details)
            TextTitle.setText(HeadLines.title)
            TextAuthor.setText(HeadLines.author)
            TextTime.setText(HeadLines.publishedAt)
            TextDetail.setText(HeadLines.description)
            TextContent.setText(HeadLines.content)
            Picasso.get().load(HeadLines.urlToImage).into(StoryImage)
        }

    }
}