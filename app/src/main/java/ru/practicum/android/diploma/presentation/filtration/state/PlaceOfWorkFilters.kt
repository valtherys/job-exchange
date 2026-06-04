package ru.practicum.android.diploma.presentation.filtration.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.presentation.filtration.workplace.state.AreaUi

@Parcelize
data class PlaceOfWorkFilters(
    val country: AreaUi? = null,
    val region: AreaUi? = null
) : Parcelable
