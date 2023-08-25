package com.example.islamicquis.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import com.example.islamicquis.FirebaseManager
import com.example.islamicquis.R
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var signInText: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var confirmPassword: EditText
    lateinit var signIn: TextView
    lateinit var logIn: TextView
    var navController: NavController? = null
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInText = view.findViewById(R.id.sign_in_text_view)
        confirmPassword = view.findViewById(R.id.confirm_password_test)
        emailText = view.findViewById(R.id.email_text)
        passwordText = view.findViewById(R.id.password_test)
        logIn = view.findViewById(R.id.login_btn)
        signIn = view.findViewById(R.id.sign_in_btn)
        auth = FirebaseAuth.getInstance()
        signIn.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            if(!password.equals(confirmPassword.text.toString())){
                Toast.makeText(requireContext(), "password and confirm password is mismatched", Toast.LENGTH_SHORT).show()
            }else {
                signUp(email, password)
            }
        }
        signInText.text = requireContext().getString(R.string.quiz_sign_in)
        logIn.visibility = View.GONE
    }
    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
    private fun isPasswordValid(password: String): Boolean {
        val minLength = 6 // Minimum password length
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return password.length >= minLength && hasUppercase && hasLowercase && hasDigit
    }
    private fun signUp(email: String, password: String) {
        // Sign Up
        if (!isEmailValid(email)) {
            // Display an error message for invalid email format
            Toast.makeText(requireContext(), "an error message for invalid email format", Toast.LENGTH_SHORT).show()
        } else if (!isPasswordValid(password)) {
            // Display an error message for invalid password format
            Toast.makeText(requireContext(), "an error message for empty password", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseManager.signUp("user@example.com", "password") { success, message ->
                if (success) {
                    Toast.makeText(requireContext(), "Success: $success", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign up failed, handle error message
                    Toast.makeText(
                        requireContext(),
                        "Error: ${message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}