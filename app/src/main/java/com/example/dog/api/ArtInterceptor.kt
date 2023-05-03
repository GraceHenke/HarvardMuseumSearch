package com.example.dog.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


private const val API = "37fa698f-5df4-4bcd-8123-7aa102ccdb88"
class ArtInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newURL: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter("apikey", API)
            .addQueryParameter("hasImage", "1")
            .addQueryParameter("size", "100")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newURL)
            .build()

        return chain.proceed(newRequest)
    }
}