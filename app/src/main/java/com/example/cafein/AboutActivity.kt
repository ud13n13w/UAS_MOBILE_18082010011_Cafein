package com.example.cafein

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.nav_header.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var tv_about = findViewById<TextView>(R.id.tv_about)
            tv_about.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        var btn_kembali = findViewById<Button>(R.id.btn_kembali)
        btn_kembali.setOnClickListener{
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }
}