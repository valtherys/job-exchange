package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.RegionsResult

interface AreaRepository {
    suspend fun getAreas(): AreaResult

    suspend fun getRegions(countryId: Int): RegionsResult

    fun getCountries(): List<FilterArea>?

    fun getRegions(): List<FilterArea>?

    fun getParentByRegionId(id: Int): FilterArea?

    fun getRegionsByCountryId(id: Int): List<FilterArea>?
}
