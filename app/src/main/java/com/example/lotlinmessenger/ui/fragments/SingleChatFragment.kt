package com.example.lotlinmessenger.ui.fragments

import android.view.View
import android.widget.TextView
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.models.CommonModel
import com.example.lotlinmessenger.models.UserModel
import com.example.lotlinmessenger.utillits.*
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView


class SingleChatFragment(private var contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference

    override fun onResume() {
        super.onResume()
        mToolbarInfo = APP_ACTIVITY.mToolbar.findViewById<View>(R.id.toolbar_info)
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
    }

    private fun initInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()) {
            mToolbarInfo.findViewById<TextView>(R.id.toolbar_chat_fullname).text = contact.fullname
        } else mToolbarInfo.findViewById<TextView>(R.id.toolbar_chat_fullname).text =
            mReceivingUser.fullname
        mToolbarInfo.findViewById<CircleImageView>(R.id.toolbar_chat_image)
            .downloadAndSetImage(mReceivingUser.photoUrl)
        mToolbarInfo.findViewById<TextView>(R.id.toolbar_chat_status).text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }

}