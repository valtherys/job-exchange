package ru.practicum.android.diploma.presentation.favorites.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoriteVacanciesState {
    data object Empty : FavoriteVacanciesState

    data object Error : FavoriteVacanciesState

    data class Content(val vacancies: List<Vacancy>) : FavoriteVacanciesState
}
