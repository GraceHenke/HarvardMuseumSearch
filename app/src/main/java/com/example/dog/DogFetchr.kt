package com.example.dog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dog.api.ArtInterceptor
import com.example.dog.api.DogApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "DogFetchr"


class DogFetchr {

    private val dogApi: DogApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(ArtInterceptor())
            .build()


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.harvardartmuseums.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        dogApi = retrofit.create(DogApi::class.java)


    }

    fun fetchArt(query: String): LiveData<List<DogPhotoItem>> {
        val responseLiveData: MutableLiveData<List<DogPhotoItem>> = MutableLiveData()
        val dogRequest: Call<DogResponse> = dogApi.fetchArt(query)

        dogRequest.enqueue(object: Callback<DogResponse> {
            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch animals", t)


            }

            override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {

                val dogResponse: DogResponse? = response.body()

                var dogItems: List<DogPhotoItem> = dogResponse?.records ?: mutableListOf()

                dogItems = dogItems.filterNot { it.url.isNullOrBlank() or it.imageURL.isNullOrBlank() }

                responseLiveData.value = dogItems
            }


        })

        return responseLiveData
    }
}