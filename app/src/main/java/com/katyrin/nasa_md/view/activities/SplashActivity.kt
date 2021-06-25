package com.katyrin.nasa_md.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katyrin.nasa_md.databinding.ActivitySplashBinding
import com.katyrin.nasa_md.utils.startRotateImage

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        startAnimation()
    }

    private fun startAnimation() {
        binding?.imageView?.startRotateImage {
            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}