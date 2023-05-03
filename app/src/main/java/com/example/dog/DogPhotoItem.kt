package com.example.dog

import com.google.gson.annotations.SerializedName


data class DogPhotoItem(
    var description: String = "",
    var title: String = "",
    @SerializedName("primaryimageurl") var imageURL: String = "",
    var url: String = ""
)