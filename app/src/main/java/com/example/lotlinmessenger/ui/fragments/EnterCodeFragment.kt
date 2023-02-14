package com.example.lotlinmessenger.ui.fragments

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.lotlinmessenger.MainActivity
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.activities.RegisterActivity
import com.example.lotlinmessenger.utillits.AUTH
import com.example.lotlinmessenger.utillits.AppTextWatcher
import com.example.lotlinmessenger.utillits.replaceActivity
import com.example.lotlinmessenger.utillits.showToast
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        view?.findViewById<EditText>(R.id.register_input_code)?.addTextChangedListener {
            AppTextWatcher {
                val string = view?.findViewById<EditText>(R.id.register_input_code)?.text.toString()
                if (string.length == 6) {
                    enterCode()
                }
            }
        }
    }

    private fun enterCode() {
        val code = view?.findViewById<EditText>(R.id.register_input_code)?.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else showToast(task.exception?.message.toString())
        }
    }
}

