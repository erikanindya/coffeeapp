package com.example.coffee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.coffee.R

class PromoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_promo, container, false)

        // Temukan WebView dari layout setelah fragment diinflate
        val webView1: WebView = rootView.findViewById(R.id.webView1)
        val webView2: WebView = rootView.findViewById(R.id.webView2)
        val webView3: WebView = rootView.findViewById(R.id.webView3)

        // Mengaktifkan JavaScript di WebView
        webView1.settings.javaScriptEnabled = true
        webView2.settings.javaScriptEnabled = true
        webView3.settings.javaScriptEnabled = true

        // Menetapkan WebChromeClient untuk WebView
        webView1.webChromeClient = WebChromeClient()
        webView2.webChromeClient = WebChromeClient()
        webView3.webChromeClient = WebChromeClient()

        // Menetapkan WebViewClient untuk mengontrol link yang dibuka di WebView
        webView1.webViewClient = WebViewClient()
        webView2.webViewClient = WebViewClient()
        webView3.webViewClient = WebViewClient()

        // Menyusun URL untuk YouTube Video (autoplay=false untuk mencegah video diputar otomatis)
        val videoUrl1 = "https://www.youtube.com/embed/TT9wIWPlOYs?autoplay=0"
        val videoUrl2 = "https://www.youtube.com/embed/o0W_0MuvlwQ?autoplay=0"
        val videoUrl3 = "https://www.youtube.com/embed/Z6Dx-o3vfJY?autoplay=0"

        // Memuat URL video YouTube di WebView pertama
        webView1.loadUrl(videoUrl1)
        // Memuat URL video YouTube di WebView kedua
        webView2.loadUrl(videoUrl2)
        // Memuat URL video YouTube di WebView ketiga
        webView3.loadUrl(videoUrl3)

        return rootView
    }
}
