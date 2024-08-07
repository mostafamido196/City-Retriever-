package com.samy.klivvrandroidchallenge.domain.usecase

import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(private val cityRepository: CityRepository) {
    operator fun invoke(prefix: String): Flow<List<City>> {
        return cityRepository.searchCities(prefix)
    }
}
