package ru.practicum.android.diploma.domain.impl.industry

import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.models.IndustryResult

class IndustryInteractorImpl(
    private val industryRepository: IndustryRepository,
) : IndustryInteractor {

    override suspend fun getIndustries(): IndustryResult {
        return industryRepository.getIndustries()
    }
}
