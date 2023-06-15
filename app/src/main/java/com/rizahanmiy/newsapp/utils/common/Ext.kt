package com.rizahanmiy.newsapp.utils.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.*
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

val categoryList = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

fun hasNetwork(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
            ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    if (this.isConnected) {
                        result = true

                    }
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    if (this.isConnected) {
                        result = true

                    }
                }
            }
        }
    }
    return result
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun overrideWebView(viewWeb : WebView, context : Context, enabled:Boolean = true){

    val newUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.2 Safari/605.1.15"

    viewWeb.settings.apply {
        userAgentString = newUA
        useWideViewPort = true
        loadWithOverviewMode = true
        javaScriptCanOpenWindowsAutomatically = true
        domStorageEnabled = true
        allowUniversalAccessFromFileURLs
        allowFileAccessFromFileURLs
        allowContentAccess = true
        cacheMode = WebSettings.LOAD_DEFAULT

        javaScriptEnabled = true

    }

    viewWeb.webViewClient = object : WebViewClient() {

        override fun onReceivedHttpAuthRequest(
            view: WebView?,
            handler: HttpAuthHandler?,
            host: String?,
            realm: String?
        ) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm)
            handler?.proceed("email", "password")
        }

        @Deprecated("Deprecated in Java")
        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            Toast.makeText(
                context,
                description + errorCode.toString() + failingUrl,
                Toast.LENGTH_LONG
            ).show()

        }
    }

    CookieManager.getInstance().setAcceptCookie(true)
    CookieManager.getInstance().flush()
    CookieManager.allowFileSchemeCookies()
    setDesktopMode(
        webView = viewWeb,
        enabled = enabled)
}

fun setDesktopMode(webView: WebView, enabled: Boolean) {
    var newUserAgent: String? = webView.settings.userAgentString

    newUserAgent = if (enabled) {
        webView.settings.userAgentString.replace("Mobile", "eliboM").replace("Android", "diordnA")
    }
    else {
        webView.settings.userAgentString.replace("eliboM", "Mobile").replace("diordnA", "Android")
    }
    webView.settings.apply {
        userAgentString = newUserAgent
        useWideViewPort = enabled
        loadWithOverviewMode = enabled
        setSupportZoom(true)
    }
//        webView.reload()
}