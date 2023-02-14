package com.example.lotlinmessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lotlinmessenger.activities.RegisterActivity
import com.example.lotlinmessenger.databinding.ActivityMainBinding
import com.example.lotlinmessenger.ui.fragments.ChatsFragment
import com.example.lotlinmessenger.ui.objects.AppDrawer
import com.example.lotlinmessenger.utillits.AUTH
import com.example.lotlinmessenger.utillits.initFirebase
import com.example.lotlinmessenger.utillits.replaceActivity
import com.example.lotlinmessenger.utillits.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }
    //Menu function
    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }
    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
    }
}