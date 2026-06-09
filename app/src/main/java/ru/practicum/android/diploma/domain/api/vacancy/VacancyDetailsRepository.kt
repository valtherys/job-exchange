package ru.practicum.android.diploma.domain.api.vacancy

import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse

interface VacancyDetailsRepository {

    suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse
}
