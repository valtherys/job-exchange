package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea

class AreaInteractorImpl(
    private val areaRepository: AreaRepository,
) : AreaInteractor {
    override suspend fun getAreas(): AreaResult {
        return areaRepository.getAreas()
    }

    override fun getCountries(): List<FilterArea>? {
        return areaRepository.getCountries()
    }

    override fun getRegions(): List<FilterArea>? {
        return areaRepository.getRegions()
    }
}
