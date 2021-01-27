package com.hightech.ecommerce.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hightech.ecommerce.MainActivity
import com.hightech.ecommerce.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        txt_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btn_sigin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}