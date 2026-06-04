package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.FilterArea

class AreasStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    fun saveAreas(areas: List<FilterArea>) {
        val map = areas.toFlatMap()
        sharedPreferences.edit {
            putString(AREAS_KEY, gson.toJson(map))
        }
    }

    fun getArea(id: Int): FilterArea? {
        val json = sharedPreferences.getString(AREAS_KEY, null) ?: return null
        return runCatching {
            val type = object : TypeToken<Map<String, FilterArea>>() {}.type
            val map: Map<String, FilterArea> = gson.fromJson(json, type)
            map[id.toString()]
        }.getOrNull()
    }

    companion object {
        const val PREFS_NAME = "areas_prefs"
        private const val AREAS_KEY = "areas_parameters"
    }
}

private fun List<FilterArea>.toFlatMap(): Map<String, FilterArea> {
    val result = mutableMapOf<String, FilterArea>()
    fun put(area: FilterArea) {
        result[area.id.toString()] = area
        area.areas.forEach { put(it) }
    }
    forEach { put(it) }
    return result
}
