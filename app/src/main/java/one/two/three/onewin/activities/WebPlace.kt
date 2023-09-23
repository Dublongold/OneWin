package one.two.three.onewin.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import one.two.three.onewin.R
import one.two.three.onewin.util.addOnBackPressedCallback
import java.io.File
import java.io.IOException


class WebPlace : AppCompatActivity() {
    private var webPartView: WebView? = null
    var fPCallback: ValueCallback<Array<Uri>>? = null
    private var nullableUri: Uri? = null
    private var reference: String? = null

    private val webSettingsSetter = object {
        fun setFirstTypeBooleans(settings: WebSettings) {
            val defaultVal = { true }
            settings.allowContentAccess = defaultVal()
            settings.useWideViewPort = defaultVal()
            settings.domStorageEnabled = defaultVal()
            settings.allowFileAccess = defaultVal()
            settings.databaseEnabled = defaultVal()
            settings.loadWithOverviewMode = defaultVal()
        }

        @Suppress("DEPRECATION")
        @SuppressLint("SetJavaScriptEnabled")
        fun setSecondTypeBooleans(settings: WebSettings) {
            val defaultVal = { true }
            settings.javaScriptEnabled = defaultVal()
            settings.javaScriptCanOpenWindowsAutomatically = defaultVal()
            settings.allowFileAccessFromFileURLs = defaultVal()
            settings.allowUniversalAccessFromFileURLs = defaultVal()
        }

        fun setContentAndCacheModes(settings: WebSettings) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        }

        fun setUserAgent(settings: WebSettings, newString: String) {
            settings.userAgentString = settings.userAgentString.replace("; wv", newString)
        }

        fun getWebChromeClient(): WebChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                fPCallback = filePathCallback
                return true
            }
        }
        fun getWebViewClient(): WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url.toString()
                return if (uri.contains("/")) {
                    Log.e("Uri", uri)
                    if (uri.contains("http")) {
                        false
                    } else {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                        true
                    }
                } else true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_place)
        webPartView = findViewById(R.id.webPlaceElement)
        reference = intent?.getStringExtra("url")
        addOnBackPressedCallback(true) {
            if(webPartView?.canGoBack() == true) {
                webPartView?.goBack()
            }
        }
        startSetting()
        webPartView?.loadUrl(reference!!)
    }

    private fun startSetting() {
        webSettingsSetter.setFirstTypeBooleans(webPartView!!.settings)
        webSettingsSetter.setSecondTypeBooleans(webPartView!!.settings)
        webSettingsSetter.setContentAndCacheModes(webPartView!!.settings)
        webSettingsSetter.setUserAgent(webPartView!!.settings, "")
        CookieManager.getInstance().apply {
            if(!acceptCookie()) {
                setAcceptCookie(true)
            }
            if(!acceptThirdPartyCookies(webPartView)) {
                setAcceptThirdPartyCookies(webPartView, true)
            }
        }
        webPartView?.webViewClient = webSettingsSetter.getWebViewClient()
        webPartView?.webChromeClient = webSettingsSetter.getWebChromeClient()
    }

    val requestPermissionLauncher = registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean? ->
        val giveMePhoto = arrayOf(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        val goToPast = Intent(Intent.ACTION_GET_CONTENT)
        val makeChoose = Intent(Intent.ACTION_CHOOSER)
        val putFile: (Uri) -> Unit = {
            giveMePhoto[0].putExtra(MediaStore.EXTRA_OUTPUT, it)
            nullableUri = it
        }
        goToPast.type = "*/*"
        makeChoose.putExtra(Intent.EXTRA_INTENT, goToPast)
        goToPast.addCategory(Intent.CATEGORY_OPENABLE)
        CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
            var pictureska: File? = null
            try {
                pictureska = File.createTempFile(
                    "pictureska",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
            } catch (ex: IOException) {
                Log.e("Pictureska", "Oh-oh-oh... Trouble...", ex)
            }
            putFile(Uri.fromFile(pictureska))
            makeChoose.putExtra(Intent.EXTRA_INITIAL_INTENTS, giveMePhoto)
            startOnActivityResultWithCodeOne(makeChoose)
        }
    }

    @Suppress("DEPRECATION")
    private fun startOnActivityResultWithCodeOne(makeChoose: Intent) {
        startActivityForResult(makeChoose, 1)
    }

    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fCallback = fPCallback ?: return
        if (resultCode.codeIsGood()) {
            if (data?.dataString != null) {
                val u = data.dataString!!.toUri()
                Log.i("Uri", u.toString())
                fCallback.onReceiveValue(arrayOf(u))
            }
            else {
                fCallback.onReceiveValue(nullableUri?.let {
                    arrayOf(it)
                })
            }
        }
        else {
            fCallback.onReceiveValue(null)
        }
        fPCallback = null
    }

    private fun Int.codeIsGood() = this == -1

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webPartView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webPartView?.restoreState(savedInstanceState)
    }
}