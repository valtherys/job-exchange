package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.storage.AreasStorage
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.RegionsResult

class AreaRepositoryImpl(
    private val networkClient: NetworkClient,
    private val areasStorage: AreasStorage,
) : AreaRepository {

    override suspend fun getAreas(): AreaResult {
        val response = networkClient.doRequest(AreasRequest)
        return when (response.resultCode) {
            NO_INTERNET -> AreaResult.NoInternet
            HTTP_SERVER_ERROR -> AreaResult.ServerError
            HTTP_OK -> {
                val data = response.data as? List<FilterAreaDto>
                if (data == null) {
                    AreaResult.Empty
                } else {
                    val areas = data.toDomain()
                    areasStorage.saveAreas(areas)
                    AreaResult.Success(areas)
                }
            }
            else -> AreaResult.Error
        }
    }

    override suspend fun getRegions(countryId: Int): RegionsResult {
        if (!areasStorage.isLoaded()) {
            val loadResult = getAreas()
            if (loadResult !is AreaResult.Success) {
                return loadResult.toRegionsResult()
            }
        }

        val regionsResult = if (countryId == AreasStorage.NO_COUNTRY_ID) {
            areasStorage.getRegions()
        } else {
            areasStorage.getRegionsByCountryId(countryId)
        }

        return regionsResult.toRegionsResult()
    }

    override fun getCountries(): List<FilterArea>? {
        return areasStorage.getCountries()
    }

    override fun getRegions(): List<FilterArea>? {
        return areasStorage.getRegions().toRegionsList()
    }

    override fun getParentByRegionId(id: Int): FilterArea? {
        return areasStorage.getParentByRegionId(id)
    }

    override fun getRegionsByCountryId(id: Int): List<FilterArea>? {
        return areasStorage.getRegionsByCountryId(id).toRegionsList()
    }

    private fun AreaResult.toRegionsResult(): RegionsResult {
        return when (this) {
            is AreaResult.Success -> {
                if (areas.isEmpty()) {
                    RegionsResult.Empty
                } else {
                    RegionsResult.Success(areas)
                }
            }
            AreaResult.NoInternet -> RegionsResult.NoInternet
            AreaResult.ServerError -> RegionsResult.ServerError
            AreaResult.Empty -> RegionsResult.Empty
            AreaResult.Error -> RegionsResult.Error
        }
    }

    private fun AreaResult.toRegionsList(): List<FilterArea>? {
        return when (this) {
            is AreaResult.Success -> areas
            else -> null
        }
    }

    private companion object {
        const val HTTP_OK = 200
        const val NO_INTERNET = -1
        const val HTTP_SERVER_ERROR = 500
    }
}
