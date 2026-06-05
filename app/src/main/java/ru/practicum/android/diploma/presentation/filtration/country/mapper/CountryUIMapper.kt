package ru.practicum.android.diploma.presentation.filtration.country.mapper

import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.ui.filtration.country.model.CountryUI

fun List<FilterArea>.toCountryUIList(): List<CountryUI> {
    return map { it.toCountryUI() }
}

fun FilterArea.toCountryUI(): CountryUI {
    return CountryUI(
        id = id,
        name = name
    )
}
