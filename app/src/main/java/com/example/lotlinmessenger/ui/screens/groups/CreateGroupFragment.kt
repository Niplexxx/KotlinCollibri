package com.example.lotlinmessenger.ui.screens.groups

import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.models.CommonModel
import com.example.lotlinmessenger.ui.fragments.BaseFragment
import com.example.lotlinmessenger.utillits.APP_ACTIVITY
import com.example.lotlinmessenger.utillits.getPlurals
import com.example.lotlinmessenger.utillits.showToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikepenz.materialize.util.KeyboardUtil.hideKeyboard

class CreateGroupFragment(private var listContacts:List<CommonModel>): BaseFragment(R.layout.fragment_create_group) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = getString(R.string.create_group)
        hideKeyboard(activity)
        initRecyclerView()
        view?.findViewById<FloatingActionButton>(R.id.create_group_btn_complete)?.setOnClickListener { showToast("Click") }
        view?.findViewById<EditText>(R.id.create_group_input_name)?.requestFocus()
        view?.findViewById<TextView>(R.id.create_group_counts)?.text = getPlurals(listContacts.size)
    }

    private fun initRecyclerView() {
        mRecyclerView = view?.findViewById<RecyclerView>(R.id.create_group_recycle_view) !!
        mAdapter = AddContactsAdapter()
        mRecyclerView.adapter = mAdapter
        listContacts.forEach {  mAdapter.updateListItems(it) }
    }
}