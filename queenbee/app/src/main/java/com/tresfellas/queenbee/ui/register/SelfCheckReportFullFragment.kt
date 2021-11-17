package com.tresfellas.queenbee.ui.register

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.tresfellas.queenbee.R
import kotlinx.android.synthetic.main.fragment_self_check_report_full.*
import kotlinx.android.synthetic.main.fragment_self_check_report_full.view.*

class SelfCheckReportFullFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_self_check_report_full, null)

        val uri = requireArguments().getString("uri")?.toUri()

        view.textView_self_check_report_full_close.setOnClickListener {
            this.dismiss()
        }

        view.imageView_self_check_report_full.setImageURI(uri)
        return AlertDialog.Builder(requireContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            .setView(view)
            .create()
    }

    companion object {
        @JvmStatic
        fun newInstance(uri: String) =
            SelfCheckReportFullFragment().apply {
                arguments = Bundle().apply {
                    putString("uri", uri)
                }
            }
    }
}
