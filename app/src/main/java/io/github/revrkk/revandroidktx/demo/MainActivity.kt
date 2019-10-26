package io.github.revrkk.revandroidktx.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.revrkk.revandroidktx.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showLongToast("Hello")
    }
}
