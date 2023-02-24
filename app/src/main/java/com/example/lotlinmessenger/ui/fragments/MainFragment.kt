package com.example.lotlinmessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.APP_ACTIVITY

class MainFragment : Fragment(R.layout.fragment_chats) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}

