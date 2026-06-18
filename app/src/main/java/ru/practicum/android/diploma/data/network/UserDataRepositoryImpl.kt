package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.domain.api.network.UserDataRepository

class UserDataRepositoryImpl : UserDataRepository {

    override fun getAuthToken(): String = BuildConfig.API_ACCESS_TOKEN
}
