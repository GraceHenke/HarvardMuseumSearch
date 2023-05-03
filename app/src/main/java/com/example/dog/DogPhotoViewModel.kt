package com.example.dog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DogPhotoViewModel(
    val query: String
): ViewModel() {

    var dogItemLiveData: LiveData<List<DogPhotoItem>>

    init {

        dogItemLiveData = DogFetchr().fetchArt(query)
    }


}