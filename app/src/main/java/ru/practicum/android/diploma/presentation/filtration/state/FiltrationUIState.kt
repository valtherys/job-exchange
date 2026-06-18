package ru.practicum.android.diploma.presentation.filtration.state

import ru.practicum.android.diploma.domain.models.FilterIndustry

data class FiltrationUIState(
    val salary: Int?,
    val onlyWithSalary: Boolean = false,
    val industry: FilterIndustry?,
    val showButtons: Boolean = false,
    val area: PlaceOfWorkFilters?
)
