package ru.practicum.android.diploma.data.storage

import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea

class AreasStorage {

    private var countries: List<FilterArea>? = null
    private var regions: List<FilterArea>? = null

    fun isLoaded(): Boolean = countries != null

    fun saveAreas(items: List<FilterArea>) {
        countries = items.filter { area -> area.parentId == null }
        regions = items
            .flatMap { country -> country.flattenRegions() }
            .distinctBy { region -> region.id }
            .sortedBy { region -> region.name }
    }

    fun getCountries(): List<FilterArea>? = countries

    fun getRegions(): AreaResult {
        return regions.orEmpty().toAreaResult()
    }

    fun getRegionsByCountryId(countryId: Int): AreaResult {
        val country = countries?.firstOrNull { area -> area.id == countryId }
        val countryRegions = country?.flattenRegions()
            ?.distinctBy { region -> region.id }
            ?.sortedBy { region -> region.name }
            .orEmpty()
        return countryRegions.toAreaResult()
    }

    fun getParentByRegionId(id: Int): FilterArea? {
        return countries?.firstOrNull { country -> country.id == getRegionById(id)?.parentId }
    }

    private fun getRegionById(id: Int): FilterArea? {
        return regions?.firstOrNull { region -> region.id == id }
    }

    private fun List<FilterArea>.toAreaResult(): AreaResult {
        return if (isEmpty()) {
            AreaResult.Empty
        } else {
            AreaResult.Success(this)
        }
    }

    private fun FilterArea.flattenRegions(): List<FilterArea> =
        areas.flatMap { region ->
            listOf(region) + region.flattenRegions()
        }

    companion object {
        const val NO_COUNTRY_ID = -1
    }
}
