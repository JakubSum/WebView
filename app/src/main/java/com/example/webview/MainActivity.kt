package com.example.webview

import android.os.Bundle
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        fun formatUrl(input: String): String {
            var formattedUrl = input.trim()

            if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                formattedUrl = "https://$formattedUrl"
            }

            if (!formattedUrl.contains("www.")) {
                //formattedUrl = formattedUrl.replace("http://", "http://www.")
                formattedUrl = formattedUrl.replace("https://", "https://www.")
            }

            return formattedUrl
        }



        val myInput: TextInputEditText = findViewById(R.id.textInputEditText)
        val Image: ImageView = findViewById(R.id.imageView)
        val Button: Button = findViewById(R.id.button)

        val myWebView: WebView = findViewById(R.id.WebView)
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl("https://www.example.pl")
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.setSupportZoom(true)

        fun closeKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val fade_in: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_in
        )

        val slide_left: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_slideleft
        )

        val slide_up: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_up
        )

        val slide_right_and_fade: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_right_and_fade
        )

        myWebView.startAnimation(slide_up);
        myInput.startAnimation(slide_left);
        Image.startAnimation(slide_left);
        //myInput.startAnimation(fade_in);

        myInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                closeKeyboard(myInput)
                myInput.animate().translationY(0f).start()
                Image.animate().translationY(0f).start()
                myInput.clearFocus()
                //myInput.text = null
                Button.visibility= View.INVISIBLE;
                true
            } else {
                false
            }
        }

        myInput.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                myInput.animate().translationY(-1800f).start()
                myInput.startAnimation(slide_up);
                Image.animate().translationY(-1800f).start()
                Image.startAnimation(slide_up);
                Button.visibility= View.VISIBLE;
                Button.startAnimation(slide_right_and_fade);
            }
            if (!hasFocus) {
               //v.clearAnimation()
                //myWebView.startAnimation(fade_in)
                closeKeyboard(myInput)
                myInput.animate().translationY(0f).start()
                Image.animate().translationY(0f).start()
                Button.visibility= View.INVISIBLE;
            }
        }

        Button.setOnClickListener {
            if (myInput.text.toString() != ""){
            val formattedUrl = formatUrl(myInput.text.toString())
            myWebView.loadUrl(formattedUrl)
            myInput.text = null
            }
            closeKeyboard(myInput)
            myInput.animate().translationY(0f).start()
            Image.animate().translationY(0f).start()
            Button.visibility= View.INVISIBLE;
            myInput.clearFocus()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}