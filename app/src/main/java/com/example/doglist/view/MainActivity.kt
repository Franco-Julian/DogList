package com.example.doglist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doglist.databinding.ActivityMainBinding
import com.example.doglist.util.DogAdapter
import com.example.doglist.viewbinding.DogViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter

    private val dogViewModel : DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        startUI()

        dogViewModel.apiError.observe(this, Observer { error ->
            if (error) Toast.makeText(this,"There is an error in the API",Toast.LENGTH_SHORT).show()
        })

        dogViewModel.dogImages.observe(this, Observer { dogImages ->
            binding.rvDogList.adapter = DogAdapter(dogImages)
        })

        dogViewModel.search.observe(this, Observer { searching ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.viewroot.windowToken, 0)
        })

    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()){
            dogViewModel.searchByName(p0.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun startUI() {
        binding.rvDogList.layoutManager = LinearLayoutManager(this)
    }


}