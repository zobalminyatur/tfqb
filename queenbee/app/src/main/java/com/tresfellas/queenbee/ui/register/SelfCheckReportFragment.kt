package com.tresfellas.queenbee.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.data.model.ShopHistoryDTO
import com.tresfellas.queenbee.databinding.FragmentSelfCheckReportBinding
import com.tresfellas.queenbee.databinding.FragmentShopHistoryBinding
import com.tresfellas.queenbee.ui.main.ui.shop.ShopHistoryAdapter
import kotlinx.android.synthetic.main.fragment_personnel.*
import kotlinx.android.synthetic.main.fragment_self_check_report.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.InputStream

class SelfCheckReportFragment : Fragment(), SelfCheckReportAdapter.OnAdapterListener {

    private val mViewModel by activityViewModels<LoginViewModel>()
    private var _binding: FragmentSelfCheckReportBinding? = null
    private lateinit var selfCheckReportAdapter: SelfCheckReportAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelfCheckReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()
        for(item in mViewModel.uriItems){
            addItemRow(item)
        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_profile_self_check_report.setOnClickListener {
            val action =
                SelfCheckReportFragmentDirections.actionSelfCheckReportFragmentToProfileAgeFragment()
            findNavController().navigate(action)
        }

//        mViewModel.isProcessBar.observe(viewLifecycleOwner,Observer{
//            if(it){
//                progressBar_profile_self_check_report.visibility = View.VISIBLE
//            }else{
//                progressBar_profile_self_check_report.visibility = View.GONE
//            }
//        })


        mViewModel.errorStatus.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), this.resources.getString(it), Toast.LENGTH_SHORT).show()
        })

    }

    private fun initRecyclerView() {
        selfCheckReportAdapter = SelfCheckReportAdapter(this)
        selfCheckReportAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
//                super.onChanged()
                println("@@@@@@SEXCHANGEED${selfCheckReportAdapter.itemCount}")
                binding.buttonProfileSelfCheckReport.isEnabled = selfCheckReportAdapter.itemCount > 1
            }
        })
        binding.recyclerViewSelfCheckReport.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = selfCheckReportAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY) {
            val url = data.data
            val reportImage = getImageWithFixedOrientationFromGallery(url)

            selfCheckReportAdapter.addReport(reportImage)
            mViewModel.uriItems.add(url!!)

//            mViewModel.uploadImage(reportImage)
            val stream = ByteArrayOutputStream()
            reportImage.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val byteArray = stream.toByteArray()
            val body = RequestBody.create(MediaType.parse("image/jpeg"), byteArray)

            println("@@@@@${byteArray.size}")
            mViewModel.uploadImageTest(body)


//            val fileName = reportImage.byteCount.toString()
//            viewModel.getFileUploadUrl(fileName, profileImage)
        }
    }

    fun addItemRow(url : Uri){
        val reportImage = getImageWithFixedOrientationFromGallery(url)
        selfCheckReportAdapter.addReport(reportImage)
    }

    private fun getImageWithFixedOrientationFromGallery(url: Uri?): Bitmap {
        return if (url == null) {
            BitmapFactory.decodeResource(resources, R.drawable.ic_logo)
        } else {
            val picturePath = requireContext().contentResolver.openInputStream(url)
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, url)
            val orientation = exifInterfaceThing(picturePath)
            rotateImage(bitmap, getExifInterfaceOrientation(orientation))
        }

    }

    fun exifInterfaceThing(inputStream: InputStream?): Int {
        return if (inputStream == null) {
            0
        } else {
            val orientation: Int = ExifInterface(inputStream).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            orientation
        }

    }

    fun getExifInterfaceOrientation(orientation: Int): Float {
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val mat = Matrix()
        mat.postRotate(angle)

        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, mat, true)
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        val pictureDialogItems =
            arrayOf(getString(R.string.self_check_report_album))
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun onAddButtonClicked() {
        showPictureDialog()
    }

    override fun onSearchButtonClicked(position: Int) {
        val dialog = SelfCheckReportFullFragment.newInstance(mViewModel.uriItems[position].toString())
        dialog.show(parentFragmentManager, null)
    }

    override fun onDeleteButtonClicked(position: Int) {
        mViewModel.uriItems.removeAt(position)
    }

    companion object {
        const val GALLERY = 1
    }

}