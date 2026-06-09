package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaInteractor {
    suspend fun getAreas(): AreaResult

    fun getCountries(): List<FilterArea>?

    fun getRegions(): List<FilterArea>?

    fun getParentByRegionId(id: Int): FilterArea?

    fun getRegionsByCountryId(id: Int): List<FilterArea>?
}
