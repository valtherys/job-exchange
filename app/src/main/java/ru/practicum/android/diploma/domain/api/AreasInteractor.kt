package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterArea

interface AreasInteractor {
    fun getAreaById(areaId: Int): FilterArea
}
