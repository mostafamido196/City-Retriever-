package com.samy.klivvrandroidchallenge.presentation.contryslist

import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.data.repository.CityRepositoryImpl
import com.samy.klivvrandroidchallenge.domain.usecase.SearchCitiesUseCase


import android.app.Application
import androidx.databinding.InverseMethod
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import androidx.lifecycle.viewModelScope
import com.samy.klivvrandroidchallenge.domain.repository.CityRepository
import com.samy.klivvrandroidchallenge.util.Utils.myLog
import com.samy.klivvrandroidchallenge.util.Utils.myTry
import com.samy.mostafasamy.utils.Constants
import com.samy.mostafasamy.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CityViewModel @Inject constructor(
    application: Application,
    private val cityRepository: CityRepository,
    private val searchCitiesUseCase: SearchCitiesUseCase,
) : AndroidViewModel(application) {

    private val _cities = MutableStateFlow<DataState>(DataState.Idle)
    val cities: StateFlow<DataState> get() = _cities

    private val _text = MutableStateFlow("")// ui element
    val text: MutableStateFlow<String> get() = _text

    init {
        _cities.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            myLog("vm init start")
            try {
                cityRepository.loadCitiesFromJson(Constants.FilesName.MainFile)
                searchCities("") // To emit all cities after loading
            }catch (e :Exception){
                myLog("e: $e")
                _cities.value = DataState.Error(0,e.message)
            }
            myLog("vm init end")
        }
    }


    fun searchCities(prefix: String) {
        _cities.value = DataState.Loading
        try {
        viewModelScope.launch(Dispatchers.IO) {
            searchCitiesUseCase.invoke(prefix)
                .onEach { result ->
                    _cities.value = DataState.Result(result)
                }.collect()
        }
        }catch (e :Exception){
            _cities.value = DataState.Error(0,e.message)

        }

    }
}