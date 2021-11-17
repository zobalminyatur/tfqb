package com.tresfellas.queenbee.ui.register

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.api.managers.TokenManager
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.ui.main.MainActivity
import com.tresfellas.queenbee.utils.SharedPreference
import kotlinx.android.synthetic.main.fragment_profile_main_location.*
import kotlinx.android.synthetic.main.fragment_terms.*
import kotlin.time.measureTimedValue

class ProfileMainLocationFragment : Fragment() {

    private val mViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_main_location, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the SDK
        Places.initialize(requireActivity().applicationContext, "AIzaSyAYFyyhr19ImzRlMDnHw54Eyx2-cyWP3xc")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(requireActivity().applicationContext)

//        autoCompletePredictions(placesClient)

        layout_main_location_search.setOnClickListener {
            startAutoComplete()
//            val action = ProfileMainLocationFragmentDirections.actionProfileMainLocationFragmentToProfileMainLocationSearchFragment()
//            findNavController().navigate(action)
        }

        textView_profile_main_location_edit.setOnClickListener {
            startAutoComplete()
        }

        button_profile_main_location.setOnClickListener {
            mViewModel.updateCurrentUser(CurrentUserManager.currentUser)

//            val action = ProfileMainLocationFragmentDirections.actionProfileMainLocationFragmentToProfileAnyLocationSearchFragment()
//            findNavController().navigate(action)
        }

        mViewModel.isProcessBar.observe(viewLifecycleOwner,Observer{
            if(it){
                progressBar_main_location.visibility = View.VISIBLE
            }else{
                progressBar_main_location.visibility = View.GONE
            }
        })

        mViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            CurrentUserManager.currentUser = it
        })

        mViewModel.toNextPage.observe(viewLifecycleOwner, Observer {
            if(it){
                saveToken()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }
        })
    }

    fun saveToken(){
        val sharedPreference = SharedPreference(requireContext())
        val accessToken =  TokenManager.accessToken
        val refreshToken = TokenManager.refreshToken

        sharedPreference.saveValueString("access_token", accessToken)
        sharedPreference.saveValueString("refresh_token", refreshToken)

    }

    fun autoCompletePredictions(placesClient : PlacesClient){
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        val token = AutocompleteSessionToken.newInstance()

        // Create a RectangularBounds object.
        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596)
        )
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request =
            FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(LatLng(-33.8749937, 151.2041382))
                .setCountries("AU", "NZ")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
//                .setQuery(query)
                .build()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                for (prediction in response.autocompletePredictions) {
                    Log.i("AutoComplete", prediction.placeId)
                    Log.i("AutoComplete", prediction.getPrimaryText(null).toString())
                }
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e("AutoComplete", "Place not found: " + exception.statusCode)
                }
            }

    }

    fun startAutoComplete(){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.NAME,Place.Field.LAT_LNG)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .setCountry("KR")
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

    }
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)

                        layout_main_location_search.visibility = View.GONE
                        layout_main_location_selected.visibility = View.VISIBLE
                        textView_profile_main_location_selected.text = place.name
                        println("@@@$place")
                        println("@@@@${place.latLng}")
                        CurrentUserManager.currentUser.location = UserDTO.LocationDTO("Point", arrayListOf(place.latLng!!.longitude,place.latLng!!.latitude)
                        )
                        CurrentUserManager.currentUser.placeName = place.name
                        val currentUser = CurrentUserManager.currentUser
                        println("@@@@${currentUser.location}@@@@@@${currentUser.placeName}")

                        Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.latLng}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(ContentValues.TAG, status.statusMessage!!)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}