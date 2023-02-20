package com.example.lotlinmessenger.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.APP_ACTIVITY


class InformationFragment : BaseFragment(R.layout.fragment_information) {
    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Информация"
    }
}