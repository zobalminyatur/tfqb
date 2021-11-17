package com.tresfellas.queenbee.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.RightNowDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentRightNowBinding
import com.tresfellas.queenbee.ui.main.ui.chat.ChatFragment
import com.tresfellas.queenbee.ui.main.ui.profile.ProfilePagerFragment

class RightNowFragment : Fragment(), RightNowAdapter.OnAdapterListener {

    private var mViewModel = RightNowViewModel()
    private var _binding: FragmentRightNowBinding? = null
    private lateinit var rightNowAdapter: RightNowAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRightNowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mViewModel.getAllUsers()

        initRecyclerView()

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.layoutWriteRightNow.setOnClickListener {
            val fragment = WriteNewMomentFragment()
            addFragmentFromBottom(fragment)
        }

        mViewModel.userListLiveData.observe(viewLifecycleOwner, Observer { list ->
            list.sortedBy { it.moment?.createdAt }
            rightNowAdapter.setItem(list)
        })

        mViewModel.isProcessBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBarRightNow.layoutProgressBar.visibility = View.VISIBLE
            } else {
                binding.progressBarRightNow.layoutProgressBar.visibility = View.GONE
            }

        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        rightNowAdapter = RightNowAdapter(
            listOf(), this
        )
        binding.rightNowRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rightNowAdapter
        }
    }

    override fun onRowClicked(item: UserDTO) {
        val fragment = ProfilePagerFragment.newInstance(item)
        addFragmentFromBottom(fragment)

    }

    override fun onChatButtonClicked(item: UserDTO) {
        val fragment = ChatFragment.newInstance(item._id, item.nickName)
        addFragmentFromBottom(fragment)

    }

    private fun addFragmentFromBottom(fragment: Fragment) {
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

    private fun checkRoyalJelly(): Boolean {
        return true
    }

}