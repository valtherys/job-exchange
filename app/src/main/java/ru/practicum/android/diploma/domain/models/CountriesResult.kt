package ru.practicum.android.diploma.domain.models

sealed interface CountriesResult {
    data class Success(val countries: List<FilterArea>) : CountriesResult

    data object NoInternet : CountriesResult

    data object ServerError : CountriesResult

    data object Empty : CountriesResult

    data object Error : CountriesResult
}
