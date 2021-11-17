package com.tresfellas.queenbee.ui.register

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.VoicemailContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.tresfellas.queenbee.R

class ProfileMainLocationSearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_main_location_search, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        // Initialize the AutocompleteSupportFragment.
//        // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment = AutocompleteSupportFragment()
//
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: ${place.name}, ${place.id}")
//            }
//
//            override fun onError(p0: Status) {
//                TODO("Not yet implemented")
//            }
//        })
        super.onViewCreated(view, savedInstanceState)

    }



}