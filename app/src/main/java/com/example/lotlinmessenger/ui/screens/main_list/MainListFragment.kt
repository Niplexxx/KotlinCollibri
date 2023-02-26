package com.example.lotlinmessenger.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.database.NODE_MAIN_LIST
import com.example.lotlinmessenger.models.CommonModel
import com.example.lotlinmessenger.utillits.*
import com.mikepenz.materialize.util.KeyboardUtil.hideKeyboard
import de.hdodenhof.circleimageview.CircleImageView

class MainListFragment : Fragment(R.layout.fragment_main_list) {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Collibri"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard(activity)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        mRecyclerView = view?.findViewById<RecyclerView>(R.id.main_list_recycle_view) !!
        mAdapter = MainListAdapter()

        // 1 запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->

                // 2 запрос
                mRefUsers.child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                        val newModel = dataSnapshot1.getCommonModel()

                        // 3 запрос
                        mRefMessages.child(model.id).limitToLast(1)
                            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                                val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                                if (tempList.isEmpty()){
                                    newModel.lastMessage = "Чат очищен"
                                } else {
                                    newModel.lastMessage = tempList[0].text
                                }

                                if (newModel.fullname.isEmpty()) {
                                    newModel.fullname = newModel.phone
                                }
                                mAdapter.updateListItems(newModel)
                            })
                    })
            }
        })

        mRecyclerView.adapter = mAdapter
    }
}

