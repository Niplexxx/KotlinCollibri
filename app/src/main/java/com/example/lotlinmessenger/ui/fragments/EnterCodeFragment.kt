package com.example.lotlinmessenger.ui.fragments

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.AppTextWatcher
import com.example.lotlinmessenger.utillits.showToast

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        view?.findViewById<EditText>(R.id.register_input_code)?.addTextChangedListener {
            AppTextWatcher {

                val string =
                    view?.findViewById<EditText>(R.id.register_input_code)!!.text.toString()
                if (string.length == 6) {
                    verifiCode()
                }
            }
        }
    }
    fun verifiCode(){
        showToast("Ok")
    }
}