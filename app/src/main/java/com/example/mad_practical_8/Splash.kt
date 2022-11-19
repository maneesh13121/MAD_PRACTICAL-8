package com.example.mad_practical_8

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.WindowCompat
import com.example.mad_practical_8.databinding.ActivitySplashBinding

abstract class Splash : AppCompatActivity(),Animation.AnimationListener {
    val TAG = "SplashActivity"
    private lateinit var binding: ActivitySplashBinding
    lateinit var animGrowSpin: Animation
    lateinit var guniframeAnimation: AnimationDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
            binding.root.setOnApplyWindowInsetsListener { _, windowInsets ->
                windowInsets
            }
            val windowInsetsController = window.insetsController ?: return
            //Configure the behaviour of the hidden system bars
            windowInsetsController.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsets.Type.systemBars())
        } else{
            this.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
        }

        binding.imgAnimLogo.setBackgroundResource(R.drawable.uvpce_animation_list)
        guniframeAnimation = binding.imgAnimLogo.background as AnimationDrawable

        animGrowSpin = AnimationUtils.loadAnimation(this,R.anim.grow)
        animGrowSpin.setAnimationListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            //Starting the animation when not in Focus
            guniframeAnimation.start()
            binding.imgAnimLogo.startAnimation(animGrowSpin)
        } else {
            //Stopping the animation when not in Focus
            guniframeAnimation.stop()
        }
    }

    override fun onAnimationStart(p0: Animation?) {
        TODO("Not yet implemented")
    }

    override fun onAnimationEnd(p0: Animation?) {
        Log.i(TAG,"onAnimationEnd: started")
        Intent(this,MainActivity::class.java).also { startActivity(it) }
        overridePendingTransition(R.anim.scale_centre_in,R.anim.scale_centre_out);
        finish()
        Log.i(TAG,"onAnimationEnd: Ended")
    }

    override fun onAnimationRepeat(p0: Animation?) {
        TODO("Not yet implemented")
    }
}