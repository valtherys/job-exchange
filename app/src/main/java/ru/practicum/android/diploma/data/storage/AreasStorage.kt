package ru.practicum.android.diploma.data.storage

import ru.practicum.android.diploma.domain.models.FilterArea

class AreasStorage {

    private var countries: List<FilterArea>? = null
    private var regions: List<FilterArea>? = null

    fun saveAreas(items: List<FilterArea>) {
        countries = items.filter { area -> area.parentId == null }
        regions = items.flatMap { country -> country.flattenRegions() }
    }

    fun getCountries(): List<FilterArea>? = countries

    fun getRegions(): List<FilterArea>? = regions

    fun getCountryById(id: Int) : FilterArea? {
        return countries?.first { country -> country.id == id }
    }

    fun getRegionById(id: Int) : FilterArea? {
        return regions?.first { region -> region.id == id }
    }

    private fun FilterArea.flattenRegions(): List<FilterArea> =
        areas.flatMap { region ->
            listOf(region) + region.flattenRegions()
        }
}
