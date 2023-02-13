package com.example.lotlinmessenger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.databinding.ActivityRegisterBinding
import com.example.lotlinmessenger.ui.fragments.EnterPhoneNumberFragment

private lateinit var mBinding: ActivityRegisterBinding
private lateinit var mToolbar: Toolbar

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        supportFragmentManager.beginTransaction()
            .add(R.id.registerDataContainer, EnterPhoneNumberFragment())
            .commit()
    }
}