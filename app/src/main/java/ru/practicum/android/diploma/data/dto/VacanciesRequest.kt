package ru.practicum.android.diploma.data.dto

import android.util.Log

data class VacanciesRequest(
    val searchText: String,
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val page: Int = 0,
    val onlyWithSalary: Boolean = false
)

fun VacanciesRequest.toMap(): Map<String, String> {
    val options = mutableMapOf<String, String>()
    area?.let { options["area"] = it.toString() }
    industry?.let { options["industry"] = it.toString() }
    options["text"] = searchText
    salary?.let { options["salary"] = it.toString() }
    options["page"] = page.toString()
    if (onlyWithSalary) {
        options["only_with_salary"] = "true"
    }
    Log.d("ИНТЕРНЕТ ПАРСИНГ ЗАПРОС", options.toString())
    return options
}
