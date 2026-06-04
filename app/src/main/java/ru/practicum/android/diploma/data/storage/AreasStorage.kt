package ru.practicum.android.diploma.data.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.FilterArea
import java.io.File

class AreasStorage(
    private val areasFile: File,
    private val gson: Gson,
) {

    fun saveAreas(areas: List<FilterArea>) {
        val map = areas.toFlatMap()
        areasFile.writeText(gson.toJson(map))
    }

    fun getArea(id: Int): FilterArea? {
        if (!areasFile.exists()) return null
        return runCatching {
            val type = object : TypeToken<Map<Int, FilterArea>>() {}.type
            val map: Map<Int, FilterArea> = gson.fromJson(areasFile.readText(), type)
            map[id]
        }.getOrNull()
    }

    companion object {
        const val AREAS_FILE_NAME = "areas.json"
    }
}

private fun List<FilterArea>.toFlatMap(): Map<Int, FilterArea> {
    val result = mutableMapOf<Int, FilterArea>()
    fun put(area: FilterArea) {
        result[area.id] = area
        area.areas.forEach { put(it) }
    }
    forEach { put(it) }
    return result
}
