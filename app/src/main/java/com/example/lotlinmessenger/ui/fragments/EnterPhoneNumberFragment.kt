package com.example.lotlinmessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lotlinmessenger.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        view?.findViewById<Button>(R.id.register_btn_next)?.setOnClickListener{
            sendCode()
        }
    }

    private fun sendCode() {
        if (view?.findViewById<EditText>(R.id.register_input_phone_number)?.text.toString().isEmpty()){
            Toast.makeText(activity, getString(R.string.register_toast_enter_phone),Toast.LENGTH_SHORT).show()
        } else {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.registerDataContainer,EnterCodeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}