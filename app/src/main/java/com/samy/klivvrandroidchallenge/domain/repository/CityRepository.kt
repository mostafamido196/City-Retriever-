package com.samy.klivvrandroidchallenge.domain.repository

import androidx.paging.Pager
import com.samy.klivvrandroidchallenge.data.model.City
import kotlinx.coroutines.flow.Flow


interface CityRepository {
    suspend fun loadCitiesFromJson(fileName: String)
    fun searchCities(prefix: String): Flow<List<City>>
}
