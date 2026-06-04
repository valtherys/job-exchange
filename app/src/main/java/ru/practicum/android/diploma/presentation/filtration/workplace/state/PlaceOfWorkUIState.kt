package ru.practicum.android.diploma.presentation.filtration.workplace.state

data class PlaceOfWorkUIState(
    val country: AreaUi? = null,
    val region: AreaUi? = null
) {
    val buttonIsShowed: Boolean
        get() = country != null || region != null
}

data class AreaUi(val id: Int, val name: String)
