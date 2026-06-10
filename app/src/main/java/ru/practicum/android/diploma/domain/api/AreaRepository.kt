package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.CountriesResult
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.RegionsResult

interface AreaRepository {
    suspend fun getAreas(): AreaResult

    suspend fun getRegions(countryId: Int): RegionsResult

    suspend fun getCountries(): CountriesResult

    fun getParentByRegionId(id: Int): FilterArea?
}
