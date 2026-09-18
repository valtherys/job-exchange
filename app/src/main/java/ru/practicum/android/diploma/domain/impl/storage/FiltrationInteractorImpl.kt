package ru.practicum.android.diploma.domain.impl.storage

import ru.practicum.android.diploma.domain.api.storage.FiltrationInteractor
import ru.practicum.android.diploma.domain.api.storage.FiltrationRepository
import ru.practicum.android.diploma.domain.models.FilterParameters

class FiltrationInteractorImpl(
    private val filtrationRepository: FiltrationRepository,
) : FiltrationInteractor {

    override fun saveFilter(filterParameters: FilterParameters) {
        filtrationRepository.saveFilter(filterParameters)
    }

    override fun getFilter(): FilterParameters {
        return filtrationRepository.getFilter()
    }

    override fun clearFilter() {
        filtrationRepository.clearFilter()
    }

    override fun hasActiveFilter(): Boolean {
        val filterParameters = filtrationRepository.getFilter()
        return filterParameters.salary != null ||
            filterParameters.hideWithoutSalary ||
            filterParameters.industryId != null ||
            filterParameters.countryId != null ||
            filterParameters.regionId != null
    }
}
