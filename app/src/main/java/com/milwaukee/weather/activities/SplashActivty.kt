package com.milwaukee.weather.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import com.milwaukee.weather.landing.activities.LandingActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LandingActivity::class.java))
        finish()
    }
}