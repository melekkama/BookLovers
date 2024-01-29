package com.example.cherubook.ui.auth.signin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.cherubook.R
import com.example.cherubook.databinding.FragmentSignInBinding
import com.example.cherubook.models.users.requests.UserSignInRequest
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.ExceptionHelpers
import com.example.cherubook.utility.LoadingState

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var viewModel: SignInViewModel

    private var fragmentMainBinding: FragmentSignInBinding? = null

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        val binding= FragmentSignInBinding.bind(view)
        fragmentMainBinding=binding


        viewModel.errorState.observe(viewLifecycleOwner) {
            ExceptionHelpers.showErrorMessageByAlertDialog(it,requireContext())
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.signinButton.isEnabled=LoadingState.Loaded==it
            when (it) {
                LoadingState.Loading -> binding.signinButton.text = "LOADING"
                LoadingState.Loaded -> binding.signinButton.text = "SIGN IN"
            }
        }

        binding.signinButton.setOnClickListener {
            val userSignIn = UserSignInRequest(
                binding.signinTextEmail.editText?.text.toString(),
                binding.signinTextPassword.editText?.text.toString(),
            )
            val validationResult = userSignIn.isValid()
            if(!validationResult.first){
                viewModel.errorState.value=validationResult.second
                return@setOnClickListener
            }
            viewModel.signIn(userSignIn).observe(viewLifecycleOwner) {
                if (it){
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

    }

    override fun onDestroyView() {
        fragmentMainBinding = null
        super.onDestroyView()
    }
}