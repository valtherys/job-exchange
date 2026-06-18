package ru.practicum.android.diploma.domain.impl.vacancy

import ru.practicum.android.diploma.domain.api.vacancy.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.vacancy.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse

class VacancyDetailInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
) : VacancyDetailInteractor {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        if (id.isEmpty()) {
            return GetVacancyDetailsResponse.Error
        }
        return vacancyDetailsRepository.getVacancyDetails(id)
    }
}
