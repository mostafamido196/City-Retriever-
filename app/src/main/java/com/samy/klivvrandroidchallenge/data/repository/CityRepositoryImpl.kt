package com.samy.klivvrandroidchallenge.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.stream.JsonReader
import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.data.model.converter.Trie
import com.samy.klivvrandroidchallenge.domain.repository.CityRepository
import com.samy.klivvrandroidchallenge.util.Utils.myLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val context: Context,
) : CityRepository {
    private val trie = Trie()

    override suspend fun loadCitiesFromJson(fileName: String) {
        try {
            context.assets.open(fileName).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                    JsonReader(bufferedReader).use { jsonReader ->
                        parseAndInsertCities(jsonReader)
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun parseAndInsertCities(jsonReader: JsonReader) {
        jsonReader.beginArray()
        while (jsonReader.hasNext()) {
            jsonReader.beginObject()

            var country = ""
            var name = ""
            var id = 0L
            var lat = 0.0
            var lon = 0.0

            while (jsonReader.hasNext()) {
                when (jsonReader.nextName()) {
                    "country" -> country = jsonReader.nextString()
                    "name" -> name = jsonReader.nextString()
                    "_id" -> id = jsonReader.nextLong()
                    "coord" -> {
                        jsonReader.beginObject()
                        while (jsonReader.hasNext()) {
                            when (jsonReader.nextName()) {
                                "lat" -> lat = jsonReader.nextDouble()
                                "lon" -> lon = jsonReader.nextDouble()
                            }
                        }
                        jsonReader.endObject()
                    }
                }
            }
            trie.insert(City(country, name, id, City.Coord(lat, lon)))
            jsonReader.endObject()
        }
        jsonReader.endArray()
    }
    override fun searchCities(prefix: String): Flow<List<City>> = flow {
        emit(trie.search(prefix))
    }
}