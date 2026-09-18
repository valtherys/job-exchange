package ru.practicum.android.diploma.domain.models

sealed interface RegionsResult {
    data class Success(val regions: List<FilterArea>) : RegionsResult

    data object NoInternet : RegionsResult

    data object ServerError : RegionsResult

    data object Empty : RegionsResult

    data object Error : RegionsResult
}
