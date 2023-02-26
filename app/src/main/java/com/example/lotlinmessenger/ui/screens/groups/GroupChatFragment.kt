package com.example.lotlinmessenger.ui.screens.groups

import android.Manifest.permission.RECORD_AUDIO
import android.annotation.SuppressLint
import android.text.style.TtsSpan.TYPE_TEXT
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lotlinmessenger.R
import com.example.lotlinmessenger.models.CommonModel
import com.example.lotlinmessenger.models.UserModel
import com.example.lotlinmessenger.ui.fragments.BaseFragment
import com.example.lotlinmessenger.utillits.*
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.net.Uri
import android.view.*
import com.example.lotlinmessenger.database.NODE_GROUPS
import com.example.lotlinmessenger.ui.screens.main_list.MainListFragment


class GroupChatFragment(private val group: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: GroupChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppChildEventListener
    private var mCountMessages = 10
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAppVoiceRecorder: AppVoiceRecorder

    override fun onResume() {
        super.onResume()
        initFields()
        initToolbar()
        initRecycleView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFields() {
        setHasOptionsMenu(true)
        mAppVoiceRecorder = AppVoiceRecorder()
        mSwipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.chat_swipe_refresh)!!
        mLayoutManager = LinearLayoutManager(this.context)
        view?.findViewById<EditText>(R.id.chat_input_message)
            ?.addTextChangedListener(AppTextWatcher {
                val string = view?.findViewById<EditText>(R.id.chat_input_message)?.text.toString()
                if (string.isEmpty()) {
                    view?.findViewById<ImageView>(R.id.chat_btn_send_message)?.visibility =
                        View.GONE
                    view?.findViewById<ImageView>(R.id.chat_btn_voice)?.visibility = View.VISIBLE
                } else {
                    view?.findViewById<ImageView>(R.id.chat_btn_send_message)?.visibility =
                        View.VISIBLE
                    view?.findViewById<ImageView>(R.id.chat_btn_voice)?.visibility = View.GONE
                }
            })


            CoroutineScope(Dispatchers.IO).launch {
            view?.findViewById<ImageView>(R.id.chat_btn_voice)?.setOnTouchListener { v, event ->
                if (checkPermission(RECORD_AUDIO)) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                            //TODO record
                        view?.findViewById<ImageView>(R.id.chat_btn_voice)?.setColorFilter(
                            ContextCompat.getColor(
                                APP_ACTIVITY,
                                R.color.teal_200
                            )
                        )
                        val messageKey = getMessageKey(group.id)
                        mAppVoiceRecorder.startRecord(messageKey)
                    } else if (event.action == MotionEvent.ACTION_UP) {
                            //TODO stop record
                            view?.findViewById<ImageView>(R.id.chat_btn_voice)?.colorFilter = null
                            mAppVoiceRecorder.stopRecord { file, messageKey ->
                                uploadFileToStorage(Uri.fromFile(file),messageKey)
                            }
                        }
                    }
                    true
                }
            }

    }

    private fun initRecycleView() {
        mRecyclerView = view?.findViewById<RecyclerView>(R.id.chat_recycle_view)!!
        mAdapter = GroupChatAdapter()
        mRefMessages = REF_DATABASE_ROOT
            .child(NODE_GROUPS)
            .child(group.id)
            .child(NODE_MESSAGES)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = mLayoutManager
        mMessagesListener = AppChildEventListener {
            val message = it.getCommonModel()

            if (mSmoothScrollToPosition) {
                mAdapter.addItemToBottom(message) {
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                }
            } else {
                mAdapter.addItemToTop(message) {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                println(mRecyclerView.recycledViewPool.getRecycledViewCount(0))
                if (mIsScrolling && dy < 0 && mLayoutManager.findFirstVisibleItemPosition() <= 3) {
                    updateData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true
                }
            }
        })
        mSwipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        mSmoothScrollToPosition = false
        mIsScrolling = false
        mCountMessages += 10
        mRefMessages.removeEventListener(mMessagesListener)
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.findViewById<View>(R.id.toolbar_info)
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(group.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)

        view?.findViewById<ImageView>(R.id.chat_btn_send_message)?.setOnClickListener {
            mSmoothScrollToPosition = true
            val message = view?.findViewById<EditText>(R.id.chat_input_message)?.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else sendMessageToGroup(
                message,
                group.id,
                TYPE_TEXT
            ){
                view?.findViewById<EditText>(R.id.chat_input_message)?.setText("")
            }
        }
    }

    private fun initInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()) {
            mToolbarInfo.findViewById<TextView>(R.id.toolbar_chat_fullname).text = group.fullname
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
        mRefMessages.removeEventListener(mMessagesListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAppVoiceRecorder.releaseRecorder()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        /* Создания выпадающего меню*/
        activity?.menuInflater?.inflate(R.menu.single_chat_action_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Слушатель выбора пунктов выпадающего меню */
        when (item.itemId) {

            R.id.menu_clear_chat -> clearChat(group.id){
                showToast("Чат очищен")
                replaceFragment(MainListFragment())
            }
            R.id.menu_delete_chat -> deleteChat(group.id){
                showToast("Чат удален")
                replaceFragment(MainListFragment())
            }
        }
        return true
    }
}