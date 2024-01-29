package com.example.cherubook.ui.launch

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import com.example.cherubook.R
import com.example.cherubook.databinding.ActivityLaunchBinding
import com.example.cherubook.ui.auth.AuthActivity
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.LoadingState

class LaunchActivity : AppCompatActivity() {
    private lateinit var viewModel: LaunchActivityViewModel
    private lateinit var binding:ActivityLaunchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        viewModel = ViewModelProvider(this)[LaunchActivityViewModel::class.java]
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var scaleX= PropertyValuesHolder.ofFloat(View.SCALE_X,1.2f)
        var scaleY= PropertyValuesHolder.ofFloat(View.SCALE_Y,1.2f)
        var animator= ObjectAnimator.ofPropertyValuesHolder(binding.imgLaunchIcon,scaleX,scaleY)
        animator.repeatMode= ObjectAnimator.REVERSE
        animator.repeatCount= Animation.INFINITE
        animator.duration=1000


        viewModel.loadingState.observe(this){
            when(it){
                LoadingState.Loaded->animator.cancel()
                LoadingState.Loading->animator.start()
            }
        }

        viewModel.loadingState.value=LoadingState.Loading
        viewModel.tokenCheck().observe(this) {
            var intent: Intent? = when (it) {
                true ->
                    Intent(this@LaunchActivity, MainActivity::class.java)
                false ->
                    Intent(this@LaunchActivity, AuthActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

}