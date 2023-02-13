package com.example.lotlinmessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.example.lotlinmessenger.R

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_actions_menu,menu)
    }
}