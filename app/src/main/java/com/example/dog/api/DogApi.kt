package com.example.dog.api

import com.example.dog.DogResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApi {
/*
    @GET("/object"
            +"?apikey=37fa698f-5df4-4bcd-8123-7aa102ccdb88"
            +"&keyword='dog'"
            +"&hasImage=1"
            +"&size=100")

*/

    @GET ("/object")
    fun fetchArt(@Query("keyword") query: String): Call<DogResponse>



}