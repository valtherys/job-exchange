package ru.practicum.android.diploma.presentation.filtration.workplace.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PlaceOfWorkUIState(
    val country: AreaUi? = null,
    val region: AreaUi? = null
) {
    val buttonIsShowed: Boolean
        get() = country != null || region != null
}

@Parcelize
data class AreaUi(val id: Int, val name: String) : Parcelable
