package com.tresfellas.queenbee.ui.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.databinding.FragmentProfileBinding
import com.tresfellas.queenbee.ui.main.ui.shop.ShopFragment
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import kotlinx.android.synthetic.main.fragment_verification_code.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userDTO : UserDTO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userDTO = requireArguments().getParcelable<UserDTO>("userDTO")!!

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.userDTO = userDTO

        binding.profileAppbarShop.setOnClickListener {
            val fragment = ShopFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                .addToBackStack(null)
                .add(R.id.frameLayout_main, fragment)
                .commit()
        }

        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nickNameAndCompatibilityAddedText =
            resources.getString(R.string.user_profile_compatibility_decription, userDTO.nickName,userDTO.compatibilityIndex.toInt())
        binding.textViewCompatibilityProfileDecription.text = nickNameAndCompatibilityAddedText

        checkCompatibility()
    }

    fun animateCompatibility(){
        val animation = ObjectAnimator.ofInt(
            binding.progressBarCompatibilityProfile,
            "progress",
            0,
            userDTO.compatibilityIndex.toInt()
        ) // see this max value coming back here, we animate towards that value

        animation.duration = 1000 // in milliseconds
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    fun checkCompatibility(){
        when {
            CurrentUserManager.currentUser.boldProfile.isEmpty() -> {
                val nickNameAddedText =
                    resources.getString(R.string.user_profile_no_my_compatibility, userDTO.nickName)
                binding.textViewNoMyCompatibilityProfileDecription.text = nickNameAddedText
                binding.layoutCompatibilityProfile.visibility = View.GONE
                binding.layoutNoMyCompatibilityProfile.visibility = View.VISIBLE
            }
            userDTO.boldProfile.isEmpty() -> {
                val nickNameAddedText =
                    resources.getString(R.string.user_profile_no_opponent_compatibility, userDTO.nickName)
                binding.textViewNoOpponentCompatibilityProfileDecription.text = nickNameAddedText
                binding.layoutCompatibilityProfile.visibility = View.GONE
                binding.layoutNoOpponentCompatibilityProfile.visibility = View.VISIBLE
            }
            else -> {
                animateCompatibility()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(userDTO: UserDTO) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("userDTO", userDTO)
                }
            }
    }
}