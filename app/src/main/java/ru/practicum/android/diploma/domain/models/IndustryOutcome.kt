package ru.practicum.android.diploma.domain.models

sealed interface IndustryOutcome {
    data class Success(val industries: List<FilterIndustry>) : IndustryOutcome

    data object NoInternet : IndustryOutcome

    data object ServerError : IndustryOutcome

    data object Error : IndustryOutcome
}
