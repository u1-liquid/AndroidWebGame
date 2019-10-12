package com.mahjongsoul.game

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.properties.Delegates

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mWebView: WebView
    private lateinit var mHostName: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mWebView = findViewById(R.id.activity_main_webview)
        mHostName = getString(R.string.app_host)

        mWebView.apply {
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(false)
                loadWithOverviewMode = true
                useWideViewPort = true
                domStorageEnabled = true
                userAgentString = getString(R.string.app_ua)
            }
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            setInitialScale(1)
        }

        if (Intent.ACTION_VIEW == intent?.action && intent?.data != null) {
            val data = intent!!.data!!
            mWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }
            }
            mWebView.loadUrl(data.toString())
        } else if (savedInstanceState == null) {
            mWebView.loadUrl("${getString(R.string.app_scheme)}://${getString(R.string.app_host)}${getString(R.string.app_path)}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mWebView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (Intent.ACTION_VIEW != intent?.action)
            mWebView.restoreState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack())
            mWebView.goBack()
        else
            super.onBackPressed()
    }

}
