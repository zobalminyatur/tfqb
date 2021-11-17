package com.tresfellas.queenbee.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.SecretProfileDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentHomeBinding
import com.tresfellas.queenbee.databinding.FragmentUserListBinding
import com.tresfellas.queenbee.ui.main.ui.chat.ChatFragment
import com.tresfellas.queenbee.ui.main.ui.profile.ProfilePagerFragment

class UserListFragment : Fragment(), UserListAdapter.OnAdapterListener {

    private var mViewModel = UserListViewModel()
    private var _binding: FragmentUserListBinding? = null
    private lateinit var userListAdapter: UserListAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()

        mViewModel.getAllUsers()


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewModel.userListLiveData.observe(viewLifecycleOwner, Observer { list ->
            userListAdapter.setItem(list)
        })

        mViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBarUserList.layoutProgressBar.visibility = View.VISIBLE
            }else{
                binding.progressBarUserList.layoutProgressBar.visibility = View.GONE
            }

        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        userListAdapter = UserListAdapter(
            listOf(), this
        )
        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userListAdapter
        }
    }

    override fun onRowClicked(item: UserDTO) {
        val fragment = ProfilePagerFragment.newInstance(item)

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

    override fun onChatButtonClicked(item: UserDTO) {
        val fragment = ChatFragment.newInstance(item._id,item.nickName)

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
