package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.domain.models.FilterArea

internal const val NO_COUNTRY_ID = -1

internal fun List<FilterArea>.buildRegionsList(countryId: Int): List<FilterArea> {
    val sourceAreas = if (countryId == NO_COUNTRY_ID) {
        this
    } else {
        listOfNotNull(findAreaById(countryId))
    }

    return sourceAreas
        .flatMap { area -> area.flattenChildrenToRegions() }
        .distinctBy { region -> region.id }
        .sortedBy { region -> region.name }
}

private fun List<FilterArea>.findAreaById(id: Int): FilterArea? {
    return firstNotNullOfOrNull { area ->
        if (area.id == id) {
            area
        } else {
            area.areas.findAreaById(id)
        }
    }
}

private fun FilterArea.flattenChildrenToRegions(): List<FilterArea> {
    return areas.flatMap { child ->
        listOf(child) + child.flattenChildrenToRegions()
    }
}
