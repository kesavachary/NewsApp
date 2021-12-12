package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //create url object pass intent ref as URL
        val url = intent.getStringExtra("URL")
        //check nullable string if url is not equal null
        if (url != null) {
            //WebView binding add settings
            detailWebView.settings.javaScriptEnabled = true
            //force mobile version of webview for mobile WebView.settings.userAgentString
            detailWebView.settings.userAgentString="Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            detailWebView.webViewClient = object : WebViewClient() {
                //on override the method
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    //setting progressbar visibility is hide when page loading finished
                    progressBar.visibility = View.GONE
                    //detailWebView is Visible
                    detailWebView.visibility = View.VISIBLE
                }

            }
            detailWebView.loadUrl(url)
        }
    }
}