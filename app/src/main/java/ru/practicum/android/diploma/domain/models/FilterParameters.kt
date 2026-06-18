package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val salary: Int? = null,
    val hideWithoutSalary: Boolean = false,
    val industryId: Int? = null,
    val industryName: String? = null,
    val countryId: Int? = null,
    val countryName: String? = null,
    val regionId: Int? = null,
    val regionName: String? = null
)
