package ru.practicum.android.diploma.presentation.filtration.state

import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi

data class PlaceOfWorkFilters(
    val country: AreaUi? = null,
    val region: AreaUi? = null
)
