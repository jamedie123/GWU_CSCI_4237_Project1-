package com.example.newsapp.Models

import java.io.Serializable

class NewsHeadlines : Serializable {
    @JvmField
    var source: Source? = null
    @JvmField
    var author = ""
    @JvmField
    var title = ""
    @JvmField
    var description = ""
    var url = ""
    @JvmField
    var urlToImage = ""
    @JvmField
    var publishedAt = ""
    @JvmField
    var content = ""
}