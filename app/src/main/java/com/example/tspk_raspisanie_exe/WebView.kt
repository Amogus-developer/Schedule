package com.example.tspk_raspisanie_exe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.tspk_raspisanie_exe.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var url = intent.extras?.getString("url").toString()
        if (url == "1"){
            url = "0"
        }
        if (url.toInt()>1){
            url = (url.toInt() - 1).toString()
        }

        val ws: WebSettings = binding.webView.settings
        ws.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(listTspk.get(url.toInt()))
    }
}