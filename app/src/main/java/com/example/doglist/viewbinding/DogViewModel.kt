package com.example.doglist.viewbinding

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doglist.model.DogResponse
import com.example.doglist.repository.APIService
import com.example.doglist.repository.HeaderInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://dog.ceo/api/breed/"

class DogViewModel : ViewModel() {

    private val _apiError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val apiError: LiveData<Boolean>
        get() = _apiError

    private val _search: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val search: LiveData<Boolean>
        get() = _search

    private val _dogImages: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val dogImages: LiveData<List<String>>
        get() = _dogImages

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(APIService::class.java).getDogsByBreeds("${query}/images")
            val dogs = call.body()
            _search.postValue(true)
            if (call.isSuccessful) {
                val images = dogs?.images ?: emptyList()
                _dogImages.postValue(images)
                _apiError.postValue(false)
                _search.postValue(false)
            } else {
                _apiError.postValue(true)
            }

        }
    }


}