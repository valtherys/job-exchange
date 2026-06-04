package ru.practicum.android.diploma.presentation.filtration.workplace.state

import ru.practicum.android.diploma.domain.models.FilterArea

data class PlaceOfWorkUIState(
    val area: FilterArea? = null,
) {
    val buttonIsShowed: Boolean
        get() = area != null
}
