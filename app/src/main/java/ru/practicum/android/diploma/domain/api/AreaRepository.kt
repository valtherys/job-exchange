package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult
import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaRepository {
    suspend fun getAreas(): AreaResult

    suspend fun getAreaById(id: Int): FilterArea?
}
