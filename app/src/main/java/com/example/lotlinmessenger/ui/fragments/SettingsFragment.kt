package com.example.lotlinmessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.models.UserModel
import com.example.lotlinmessenger.utillits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mikepenz.materialize.util.KeyboardUtil
import de.hdodenhof.circleimageview.CircleImageView

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var settings_shange_photo: CircleImageView
    private var storageRef = Firebase.storage
    private lateinit var uri: Uri


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
//        storageRef = FirebaseStorage.getInstance()
//        val galleryImage = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            ActivityResultCallback {
//                settings_shange_photo.setImageURI(it)
//                if (it != null) {
//                    uri = it
//                }
//            }
//        )
//        galleryImage.launch("image/*")
//        storageRef.getReference("images").child(System.currentTimeMillis().toString())
//            .putFile(uri)
//            .addOnSuccessListener { task ->
//                task.metadata?.reference?.downloadUrl
//                    ?.addOnSuccessListener {
//                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//                        val mapImages = mapOf(
//                            "uri" to it.toString()
//                        )
//                        val databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
//                        databaseReference.child(userId).setValue(mapImages)
//                            .addOnSuccessListener {
//                                showToast("Изображение загружено")
//                            }
//                            .addOnFailureListener{error ->
//                                showToast("Произошла ошибка!")
//                            }
//                    }
//            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null
        ) {
            val uri = CropImage
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
        }
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