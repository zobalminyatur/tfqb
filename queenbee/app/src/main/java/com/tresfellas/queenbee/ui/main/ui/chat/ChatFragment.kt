package com.tresfellas.queenbee.ui.main.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.model.ChatsDTO
import com.tresfellas.queenbee.data.model.WriteMessageDTO
import com.tresfellas.queenbee.databinding.FragmentChatBinding
import com.tresfellas.queenbee.utils.Constants
import com.tresfellas.queenbee.utils.TimeUtil
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URI
import io.socket.client.Manager


class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

//    private lateinit var userDTO: UserDTO

    private lateinit var userId : String
    private lateinit var userNickName: String

    lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val uri = URI.create("https://queen-bee-1-0-0.herokuapp.com/api/v1/")
        val manager = Manager(URI(Constants.SOCKET_BASE_URL))
        val socket = manager.socket("/")
        mSocket = socket
//        mSocket = IO.socket(uri)

        mSocket.on(Socket.EVENT_CONNECT, onConnect)
//        mSocket.on(Socket.EVENT_CONNECT_ERROR,onConnectError)
        mSocket.on("joinedRoom",onJoinedRoom)
        mSocket.on("joinError",onJoinError)
        mSocket.on("wroteMessage", onNewMessage)
        mSocket.on("leftRoom", onLeaveRoom)
        mSocket.on("leaveError",onLeaveError)


//        userDTO = requireArguments().getParcelable<UserDTO>("userDTO")!!

        userId = requireArguments().getString("userId")!!
        userNickName = requireArguments().getString("userNickName")!!

        chatViewModel =
            ViewModelProvider(this).get(ChatViewModel::class.java)

        chatViewModel.getChatRoom(userId)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textViewTitleChat
        textView.text = userNickName

        initRecyclerView()

        return root
    }

    lateinit var mSocket: Socket
    lateinit var roomId : String
    var users: Array<String> = arrayOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        try {
//
//        } catch (e: URISyntaxException) {
//            Log.e("ChatFragment", e.reason)
//        }
        chatViewModel.chatsDTO.observe(viewLifecycleOwner, {
            chatAdapter.setItem(it.messages)
            roomId = it.roomId
            println("@@@@SOCKETCONNECT$roomId")
            mSocket.connect()

        })

        binding.imageViewChatMessageAdd.setOnClickListener {
            println("@@@@@@AddSHITT")

        }

        binding.imageViewChatMessageSend.setOnClickListener {
            val connected = mSocket.connected()
            println("@@@@@@$connected")
            if(binding.editTextChatMessage.text.isNotBlank()){
                val text = binding.editTextChatMessage.text.toString()
                val messageDTO = WriteMessageDTO(roomId,text,CurrentUserManager.currentUser._id,userId)
                val json = JSONObject()
                val date = TimeUtil.getCurrentDateToString()
                json.put("createdAt",date)
                json.put("roomId",roomId)
                json.put("text",text)
                json.put("from",CurrentUserManager.currentUser._id)
                json.put("to",userId)

                println("@@@@@@TEXTSENT$text")
                binding.editTextChatMessage.text.clear()
                mSocket.emit("writeMessage",json)
            }
        }

        binding.chatAppbarMore.setOnClickListener {
            val bottomSheetDialog = ChatMoreDialog.newInstance(roomId, userId)
            bottomSheetDialog.show(childFragmentManager, null)
        }

        chatViewModel.isProcessBar.observe(viewLifecycleOwner, {
            if(it){
                binding.progressBarChatMessage.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarChatMessage.layoutProgressBar.visibility = View.GONE
            }
        })
    }

    val json = JSONObject()

    val onConnect: Emitter.Listener = Emitter.Listener {

        println("@@@@@CONNECTED")
        json.put("roomId",roomId)
        json.put("myId",CurrentUserManager.currentUser._id)
        mSocket.emit("joinRoom",json)
        Log.d("Tag", "Socket is connected with ${roomId}")
    }

//    val onConnectError: Emitter.Listener = Emitter.Listener {

//        val jsonObj: JSONObject = it[0] as JSONObject

//        println("@@@@@FUCKFUCFKCUFKCUFKCUFCKU$jsonObj")
//    }

    val onJoinedRoom : Emitter.Listener = Emitter.Listener {
        requireActivity().runOnUiThread {
            chatViewModel.isProcessBar.value = false
        }
        Log.d("Tag","Joined room")
        val string = it[0].toString()
        println("@@@$string")
    }

    val onJoinError : Emitter.Listener = Emitter.Listener {
        requireActivity().runOnUiThread {
            chatViewModel.isProcessBar.value = false
        }
        val string = it[0].toString()
        Log.d("Join Error",string)
    }
    val onLeaveError : Emitter.Listener = Emitter.Listener {
        val string = it[0].toString()
        Log.d("Leave Error",string)
    }

    val onNewMessage = Emitter.Listener {
        println("@@@@NEWMESSAGE")
        val json = it[0].toString()
        val message = GsonBuilder().create().fromJson(json,ChatsDTO.Messages::class.java)
//        val message = GsonBuilder().create().fromJson(jsonObj, ChatsDTO.Messages::class.java)
        requireActivity().runOnUiThread {
            chatAdapter.addMessage(message)
            binding.recyclerViewChatMessage.scrollToPosition(0)
        }
        Log.d("on", "New message has been triggered.")
        Log.d("new msg : ", it[0].toString())
    }

    val onLeaveRoom = Emitter.Listener {
        Log.d("on", "Logout has been triggered.")

        try {
            val json = it[0].toString()
            Log.d("logout ", "disconnected$json")
            //Disconnect socket!
            mSocket.disconnect()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    val onMessageRecieved: Emitter.Listener = Emitter.Listener {
        try {
            val receivedData: Any = it[0]
            Log.d("Tag", receivedData.toString())

        } catch (e: Exception) {
            Log.e("Tag", "error", e)
        }
    }

    val onNewUser: Emitter.Listener = Emitter.Listener {

        var data = it[0]
        if (data is String) {
            users = data.split(",").toTypedArray()
            for (a: String in users) {
                Log.d("user", a)
            }
        } else {
            Log.d("error", "Something went wrong")
        }

    }

    override fun onDestroy() {
        mSocket.emit("leaveRoom",json)
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.recyclerViewChatMessage.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,true)
            adapter = chatAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: String, userNickName : String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString("userId", userId)
                    putString("userNickName", userNickName)
                }
            }
    }
}