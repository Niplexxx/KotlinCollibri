package com.example.lotlinmessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.APP_ACTIVITY
import com.mikepenz.materialize.util.KeyboardUtil.hideKeyboard

class MainFragment : Fragment(R.layout.fragment_chats) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Группы"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard(activity)
    }
}

