package com.example.cherubook.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cherubook.R
import com.example.cherubook.databinding.FragmentProfileBinding
import com.example.cherubook.databinding.FragmentSignInBinding
import com.example.cherubook.ui.auth.signin.SignInViewModel
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.SharedPreferenceHelpers

class ProfileFragment  : Fragment(R.layout.fragment_profile)   {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding : FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding= FragmentProfileBinding.bind(view)
        MainActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        MainActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        val profile= SharedPreferenceHelpers.getProfile();

        binding.profileFullname.text=profile?.fullName
        binding.profileEmail.text=profile?.email
        binding.profileUsername.text= "@${profile?.userName}"
        binding.profileAbout.text=profile?.about


        if (profile?.photo!=null)
            Glide.with(this)
                .load(profile.photo)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.user_icon)
                .error(R.drawable.user_icon)
                .into(binding.profilePhoto)

        if (profile?.banner!=null)
            Glide.with(this)
                .load(profile.banner)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.banner)
                .error(R.drawable.banner)
                .into(binding.profileBanner)


    }
}