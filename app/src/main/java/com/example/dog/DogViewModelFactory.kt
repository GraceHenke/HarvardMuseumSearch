package com.example.dog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DogViewModelFactory(
    private val query: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return DogPhotoViewModel(query) as T
    }
}