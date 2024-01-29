package com.example.cherubook.ui.auth.signup

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.cherubook.R
import com.example.cherubook.databinding.FragmentSignUpBinding
import com.example.cherubook.models.users.requests.UserSignUpRequest
import com.example.cherubook.utility.ExceptionHelpers
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var viewModel: SignUpViewModel

    private var fragmentMainBinding: FragmentSignUpBinding? = null

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        val binding= FragmentSignUpBinding.bind(view)
        fragmentMainBinding=binding

        var viewPager = requireActivity().findViewById<ViewPager2>(R.id.auth_viewpager)


        viewModel.errorState.observe(viewLifecycleOwner) {
            ExceptionHelpers.showErrorMessageByAlertDialog(it,view.context)
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.signupButton.isEnabled=LoadingState.Loaded==it
            when (it) {
                LoadingState.Loading -> binding.signupButton.text = "LOADING"
                LoadingState.Loaded -> binding.signupButton.text = "SIGN UP"
                else -> throw IllegalStateException()
            }
        }

        binding.signupButton.setOnClickListener {
            var userSignUp = UserSignUpRequest(
                binding.signupTextFirstName.editText?.text.toString(),
                binding.signupTextLastName.editText?.text.toString(),
                binding.signupTextEmail.editText?.text.toString(),
                binding.signupTextUsername.editText?.text.toString(),
                binding.signupTextPassword.editText?.text.toString(),
            )
            val validationResult = userSignUp.isValid()
            if(!validationResult.first){
                viewModel.errorState.value=validationResult.second
                return@setOnClickListener
            }

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner) {
                if (!it) return@observe
                clearForm()
                viewPager.currentItem = 0
                onAlertDialog(view)
            }
        }

    }

    private fun clearForm(){
        fragmentMainBinding?.signupTextFirstName?.editText?.text?.clear()
        fragmentMainBinding?.signupTextLastName?.editText?.text?.clear()
        fragmentMainBinding?.signupTextEmail?.editText?.text?.clear()
        fragmentMainBinding?.signupTextUsername?.editText?.text?.clear()
        fragmentMainBinding?.signupTextPassword?.editText?.text?.clear()
    }
    private fun onAlertDialog(view: View) {
        var builder = AlertDialog.Builder(view.context)
        builder.setMessage(R.string.registration_complete)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
    override fun onDestroyView() {
        fragmentMainBinding = null
        super.onDestroyView()
    }
}