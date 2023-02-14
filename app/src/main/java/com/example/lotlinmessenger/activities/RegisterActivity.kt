package com.example.lotlinmessenger.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.databinding.ActivityRegisterBinding
import com.example.lotlinmessenger.ui.fragments.EnterPhoneNumberFragment
import com.example.lotlinmessenger.utillits.initFirebase
import com.example.lotlinmessenger.utillits.replaceFragment

private lateinit var mBinding: ActivityRegisterBinding

@SuppressLint("StaticFieldLeak")
private lateinit var mToolbar: Toolbar

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment())
    }
}