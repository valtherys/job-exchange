package ru.practicum.android.diploma.domain.api.network

interface UserDataRepository {
    fun getAuthToken(): String?
}
