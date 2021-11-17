package com.tresfellas.queenbee.ui.main.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.ChatRoomsDTO
import com.tresfellas.queenbee.databinding.FragmentChatListBinding
import com.tresfellas.queenbee.ui.main.ui.home.RightNowAdapter

class ChatListFragment : Fragment(), ChatListAdapter.OnAdapterListener {

    private lateinit var chatViewModel: ChatListViewModel
    private var _binding: FragmentChatListBinding? = null
    private lateinit var chatListAdapter: ChatListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        chatViewModel =
            ViewModelProvider(this).get(ChatListViewModel::class.java)

        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        chatViewModel.getChatRoomList()

        initRecyclerView()

        arguments?.let{
            val userId = it.getString("userId")!!
            val nickName = it.getString("nickName")!!
            toChatFragment(userId,nickName)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewModel.chatRoomsDTO.observe(viewLifecycleOwner, Observer {
            if(it.chats.isEmpty()){
                binding.layoutEmptyChatList.visibility = View.VISIBLE
            }else{
                binding.layoutEmptyChatList.visibility = View.GONE
                chatListAdapter.setItem(it.chats)
            }
        })
        chatViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBarChatList.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarChatList.layoutProgressBar.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(){
        chatListAdapter = ChatListAdapter(this)
        binding.recyclerViewChatList.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onChatRowClicked(chatRooms: ChatRoomsDTO.ChatRooms) {
        toChatFragment(chatRooms.userId,chatRooms.nickName)
    }

    private fun toChatFragment(userId: String, userNickname: String){
        val fragment = ChatFragment.newInstance(userId,userNickname)

        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_bottom,
            R.anim.exit_to_bottom,
            R.anim.enter_from_bottom,
            R.anim.exit_to_bottom
        )
            .addToBackStack(null)
            .add(R.id.frameLayout_main, fragment)
            .commit()
    }

}