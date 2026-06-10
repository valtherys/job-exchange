package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.CountriesResult
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.RegionsResult

class AreaInteractorImpl(
    private val areaRepository: AreaRepository,
) : AreaInteractor {
    override suspend fun getAreas(): AreaResult {
        return areaRepository.getAreas()
    }

    override suspend fun getRegions(countryId: Int): RegionsResult {
        return areaRepository.getRegions(countryId)
    }

    override suspend fun getCountries(): CountriesResult {
        return areaRepository.getCountries()
    }

    override fun getParentByRegionId(id: Int): FilterArea? {
        return areaRepository.getParentByRegionId(id)
    }
}
