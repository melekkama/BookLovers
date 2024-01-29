package com.example.cherubook.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cherubook.R
import com.example.cherubook.databinding.ActivityAuthBinding
import com.example.cherubook.databinding.ActivityMainBinding
import com.example.cherubook.ui.auth.signin.SignInFragment
import com.example.cherubook.ui.auth.signup.SignUpFragment
import com.example.cherubook.utility.SharedPreferenceHelpers

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPreferenceHelpers.clearAuth()
        var pagerAdapter=ScreenSlidePagerAdapter(this)
        pagerAdapter.addFragment(SignInFragment())
        pagerAdapter.addFragment(SignUpFragment())
        binding.authViewpager.adapter=pagerAdapter
    }

    private  inner  class  ScreenSlidePagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        var fragments=ArrayList<Fragment>()
        override fun getItemCount(): Int {
            return  fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(f: Fragment){
            fragments.add(f)
        }

    }
}