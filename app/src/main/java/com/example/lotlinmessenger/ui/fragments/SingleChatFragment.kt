package com.example.lotlinmessenger.ui.fragments

import android.view.View
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.models.CommonModel
import com.example.lotlinmessenger.utillits.APP_ACTIVITY


class SingleChatFragment(contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.findViewById<View>(R.id.toolbar_info).visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.findViewById<View>(R.id.toolbar_info).visibility = View.GONE
    }

}