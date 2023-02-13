package com.example.lotlinmessenger.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.lotlinmessenger.R

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        view?.findViewById<EditText>(R.id.register_input_code)?.addTextChangedListener { object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val string = view?.findViewById<EditText>(R.id.register_input_code)!!.text.toString()
                if (string.length==6){
                    verifiCode()
                }
            }
        }}
    }
    fun verifiCode(){
        Toast.makeText(activity,"Ok",Toast.LENGTH_SHORT).show()
    }
}