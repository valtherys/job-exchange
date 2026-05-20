package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Salary
import java.text.NumberFormat
import java.util.Locale

private val numberFormat = NumberFormat.getInstance(Locale("ru", "RU"))

private val currencySuffixes = mapOf(
    "RUR" to " ₽",
    "RUB" to " ₽",
    "USD" to " $",
    "EUR" to " €",
    "BYR" to " Br",
    "KZT" to " ₸",
    "UAH" to " ₴",
    "AZN" to " ₼",
    "UZS" to " so'm",
    "GEL" to " ₾",
    "KGT" to " с",
)

fun formatSalary(salary: Salary?): String {
    if (salary == null) {
        return ""
    }
    val from = salary.from
    val to = salary.to
    val currencySuffix = salary.currency?.let { code ->
        currencySuffixes[code.uppercase()] ?: " $code"
    }.orEmpty()

    val amountText = when {
        from != null && to != null -> "от ${formatAmount(from)} до ${formatAmount(to)}"
        from != null -> "от ${formatAmount(from)}"
        to != null -> "до ${formatAmount(to)}"
        else -> return "Зарплата не указана"
    }
    return amountText + currencySuffix
}

private fun formatAmount(value: Int): String = numberFormat.format(value)
