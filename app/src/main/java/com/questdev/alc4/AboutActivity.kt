package com.questdev.alc4

import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_about.*
import org.apache.http.conn.ssl.SSLSocketFactory.SSL

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)

        setupWebView()
    }

    private fun setupWebView() {
        val webView = findViewById<WebView>(R.id.webview_alc_about)
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                val builder = AlertDialog.Builder(this@AboutActivity)
                var message = "SSL Certificate Error. "
                when (error?.primaryError) {
                    SslError.SSL_UNTRUSTED -> message += "The Certificate authority is untrusted. "
                    SslError.SSL_EXPIRED -> message += "The certificate has expired. "
                    SslError.SSL_IDMISMATCH -> message += "The certificate hostname mismatch. "
                    SslError.SSL_NOTYETVALID -> message += "The certificate is not yet valid. "
                }
                message += "Do you want to continue anyway?"
                builder.setTitle("SSL Certificate Error")
                builder.setMessage(message)

                builder.setPositiveButton("continue") {_, _ ->
                    handler!!.proceed()
                }

                builder.setNegativeButton("cancel") {_, _ ->
                    handler!!.cancel()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.blockNetworkLoads = false
        webView.loadUrl("https://www.andela.com/alc/")
    }

}
