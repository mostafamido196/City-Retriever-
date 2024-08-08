package com.samy.klivvrandroidchallenge.presentation.contryslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.databinding.ActivityMainBinding
import com.samy.klivvrandroidchallenge.presentation.map.MapActivity
import com.samy.klivvrandroidchallenge.util.Utils.myLog
import com.samy.mostafasamy.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cityViewModel: CityViewModel by viewModels()

    @Inject
    lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myLog("onCreate")
        setup()
        observe()
    }

    private fun setup() {
        binding.rv.adapter = adapter
        binding.viewModel = cityViewModel
        binding.lifecycleOwner = this

        adapter.setOnItemClickListener {
            myLog("setOnItemClickListener city: ${it}")
            try {
                val intent = Intent(this, MapActivity::class.java).apply {
                    putExtra("name", it.name)
                    putExtra("country", it.country)
                    putExtra("lat", it.coord.lat)
                    putExtra("lon", it.coord.lon)
                }
                startActivity(intent)
            } catch (e: Exception) {
                myLog("e: $e")
            }
        }
    }


    private fun observe() {
        lifecycleScope.launch {
            launch {
                dataObserver()
            }
            launch {
                searchObserver()
            }
        }
    }

    private suspend fun dataObserver() {
        cityViewModel.cities.collect {
            when (it) {
                is DataState.Idle -> {
                    return@collect
                }

                is DataState.Loading -> {
                    showProgress(true)
                }

                is DataState.Error -> {
                    showProgress(false)
                    it.handleErrors(this@MainActivity)
                }

                is DataState.Result<*> -> {
                    try {
                        showProgress(false)
                        handleResult(it.response as List<City>)
                    } catch (e: Exception) {
                        myLog("e: ${e.message}")
                    }
                }
            }

        }
    }

    private suspend fun searchObserver() {
        cityViewModel.text.collect { text ->
            val t = text.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
            cityViewModel.searchCities(t)
        }
    }

    fun handleResult(response: List<City>) {
        adapter.submitList(response)
        myLog("MainAc size ${response.size}")
    }

    private fun showProgress(b: Boolean) {
        if (b) {
            binding.progressbar.visibility = View.VISIBLE
            binding.rv.visibility = View.GONE
        } else {
            binding.progressbar.visibility = View.GONE
            binding.rv.visibility = View.VISIBLE
        }
    }
}