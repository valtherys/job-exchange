package ru.practicum.android.diploma.presentation.filtration.region.mapper

import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi

fun List<FilterArea>.toRegionUiList(): List<RegionUi> = map { region -> region.toRegionUi() }

fun FilterArea.toRegionUi(): RegionUi = RegionUi(
    id = id,
    name = name,
)
