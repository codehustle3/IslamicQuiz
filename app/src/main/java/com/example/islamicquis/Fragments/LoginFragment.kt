package com.example.islamicquis.Fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.islamicquis.FirebaseManager
import com.example.islamicquis.R
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var confirmPassword: EditText
    lateinit var signIn: TextView
    lateinit var logIn: TextView
    var navController: NavController? = null
    lateinit var auth: FirebaseAuth
    private lateinit var emailText:EditText
    private lateinit var passwordText: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirmPassword = view.findViewById(R.id.confirm_password_test)
        signIn = view.findViewById(R.id.sign_in_btn)
        emailText = view.findViewById(R.id.email_text)
        passwordText = view.findViewById(R.id.password_test)
        logIn = view.findViewById(R.id.login_btn)
        auth = FirebaseAuth.getInstance()
        navController = view.findNavController()
        signIn.setOnClickListener {
            var navOptions = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right).build()
            navController!!.navigate(R.id.signInFragment, null, navOptions)
        }
        confirmPassword.visibility = View.GONE
        logIn.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()

            login(email, password)
        }
    }
    fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
    fun isPasswordValid(password: String): Boolean {
        val minLength = 6 // Minimum password length
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return password.length >= minLength && hasUppercase && hasLowercase && hasDigit
    }
    fun login(email: String, password: String) {
        // Login
        if (!isEmailValid(email)) {
            // Display an error message for invalid email format
            Toast.makeText(requireContext(), "an error message for invalid email format", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            // Display an error message for empty password
            Toast.makeText(requireContext(), "an error message for empty password", Toast.LENGTH_SHORT).show()

        } else {
            FirebaseManager.login("user@example.com", "password") { success, message ->
                if (success) {
                    Toast.makeText(requireContext(), "Success: $message", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign up failed, handle error message
                    Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}