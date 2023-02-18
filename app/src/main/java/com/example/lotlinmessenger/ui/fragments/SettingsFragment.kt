package com.example.lotlinmessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lotlinmessenger.MainActivity
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.activities.RegisterActivity
import com.example.lotlinmessenger.utillits.AUTH
import com.example.lotlinmessenger.utillits.USER
import com.example.lotlinmessenger.utillits.replaceActivity
import com.example.lotlinmessenger.utillits.replaceFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        view?.findViewById<TextView>(R.id.settings_bio)?.text = USER.bio
        view?.findViewById<TextView>(R.id.settings_full_name)?.text = USER.fullname
        view?.findViewById<TextView>(R.id.settings_phone_number)?.text = USER.phone
        view?.findViewById<TextView>(R.id.settings_status)?.text = USER.status
        view?.findViewById<TextView>(R.id.settings_username)?.text = USER.username
        view?.findViewById<ConstraintLayout>(R.id.settings_btn_change_username)
            ?.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        view?.findViewById<ConstraintLayout>(R.id.settings_btn_change_bio)
            ?.setOnClickListener { replaceFragment(ChangeBioFragment()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_actions_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}