package com.tresfellas.queenbee.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.ui.register.LoginActivity

class OnBoardFragment : Fragment() {

    private lateinit var decription: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_on_board, container, false)
        val description = requireArguments().getString("description")
        val position = requireArguments().getInt("position")
        val button = view.findViewById<AppCompatButton>(R.id.onboard_next_button)
        decription = view.findViewById(R.id.onboard_textview)
        decription.text = description
        if (position == 2) {
            button.visibility = View.VISIBLE
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = 2000
            button.startAnimation(anim)
        }
        button.setOnClickListener {
            (requireActivity() as OnBoardActivity).isActivityStart = true

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        return view.rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(description: String,position:Int) =
            OnBoardFragment().apply {
                arguments = Bundle().apply {
                    putString("description", description)
                    putInt("position",position)
                }
            }
    }
}