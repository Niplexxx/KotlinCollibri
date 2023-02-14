package com.example.lotlinmessenger.ui.fragments

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.replaceFragment
import com.example.lotlinmessenger.utillits.showToast

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        view?.findViewById<Button>(R.id.register_btn_next)?.setOnClickListener{
            sendCode()
        }
    }

    private fun sendCode() {
        if (view?.findViewById<EditText>(R.id.register_input_phone_number)?.text.toString().isEmpty()){
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
            replaceFragment(EnterCodeFragment())
        }
    }
}