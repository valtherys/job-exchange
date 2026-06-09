package ru.practicum.android.diploma.domain.api.industry

import ru.practicum.android.diploma.domain.models.IndustryResult

interface IndustryInteractor {
    suspend fun getIndustries(): IndustryResult
}
