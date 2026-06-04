package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.FilterArea

class AreasStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    fun saveAreas(areas: List<FilterArea>) {
        val map = areas.associateBy({ it.id }, { it })
        sharedPreferences.edit {
            putString(FILTER_PARAMETERS_KEY, gson.toJson(map))
        }
    }

    fun getArea(id: String): FilterArea? {
        val json = sharedPreferences.getString(FILTER_PARAMETERS_KEY, null) ?: return null
        return runCatching {
            gson.fromJson(json,)
        }.getOrNull()
    }

    companion object {
        const val PREFS_NAME = "areas_prefs"
        private const val FILTER_PARAMETERS_KEY = "areas_parameters"
    }
}
