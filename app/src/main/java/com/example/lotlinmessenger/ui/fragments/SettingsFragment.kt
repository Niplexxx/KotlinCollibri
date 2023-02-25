package com.example.lotlinmessenger.ui.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.lotlinmessenger.MainActivity
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.utillits.*
import com.mikepenz.materialize.util.KeyboardUtil
import de.hdodenhof.circleimageview.CircleImageView


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Личный кабинет"
        setHasOptionsMenu(true)
        initFields()
        KeyboardUtil.hideKeyboard(activity)
    }

    private fun initFields() {
        view?.findViewById<TextView>(R.id.settings_bio)?.text = USER.bio
        view?.findViewById<TextView>(R.id.settings_full_name)?.text = USER.fullname
        view?.findViewById<TextView>(R.id.settings_phone_number)?.text = USER.phone
        view?.findViewById<TextView>(R.id.settings_status)?.text = USER.state
        view?.findViewById<TextView>(R.id.settings_username)?.text = USER.username
        view?.findViewById<ConstraintLayout>(R.id.settings_btn_change_username)
            ?.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        view?.findViewById<ConstraintLayout>(R.id.settings_btn_change_bio)
            ?.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        view?.findViewById<CircleImageView>(R.id.settings_shange_photo)
            ?.setOnClickListener { changePhotoUser() }
    }

    private fun changePhotoUser() {
        startCrop(APP_ACTIVITY)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            val uriFilePath = context?.let { result.getUriFilePath(it) } // optional usage
        } else {
            // An error occurred.
            val exception = result.error
        }
    }
    private fun startCrop(APP_ACTIVITY: MainActivity) {
        // Start picker to get image for cropping and then use the image in cropping activity.
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )

        // Start cropping activity for pre-acquired image saved on the device and customize settings.
        cropImage.launch(
            options(uri = Uri.EMPTY) {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_actions_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AppStates.updateState(AppStates.OFFLINE)
                AUTH.signOut()
                restartActivity()
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}
