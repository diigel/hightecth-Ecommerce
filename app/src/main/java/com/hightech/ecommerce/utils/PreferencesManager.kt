package com.hightech.ecommerce.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.asLiveData
import com.hightech.ecommerce.EcommerceApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

object PreferencesManager {

    private val context = EcommerceApplication.getApplicationContext()
    private val dataStore: DataStore<Preferences> = context.createDataStore("preferences")

    private val isFirstLaunch = booleanPreferencesKey("is_first_launch")

    //auth
    private val token = stringPreferencesKey("token")
    private val authDeviceUniqId = stringPreferencesKey("auth_device_uni_id")
    private val authDevicedDev = stringPreferencesKey("auth_device_dev")

    //user
    private val userId = stringPreferencesKey("user_id")
    private val userName = stringPreferencesKey("user_name")
    private val userEmail = stringPreferencesKey("user_email")
    private val userPassword = stringPreferencesKey("user_password")
    private val userPhone = stringPreferencesKey("user_phone")

    // save data
    suspend fun saveIsFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit {
            it[PreferencesManager.isFirstLaunch] = isFirstLaunch
        }
    }

    // get data by async flow
    fun getIsFirstLaunchAsync() = dataStore.data.map {
        it[isFirstLaunch] ?: true
    }

    // only get data
    fun getIsFirstLaunch() = runBlocking {
        dataStore.data.first()[isFirstLaunch]
    }

    // get data by async livedata
    fun getIsFirstLaunchLiveData(coroutineContext: CoroutineContext) =
        getIsFirstLaunchAsync().asLiveData(coroutineContext)

    // auth
    fun getAuthToken() = runBlocking {
        dataStore.data.first()[token]
    }

    fun getAuthDeviceUniqId() = runBlocking {
        dataStore.data.first()[authDeviceUniqId]
    }

    fun getAuthDeviceDev() = runBlocking {
        dataStore.data.first()[authDevicedDev]
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit {
            it[PreferencesManager.token] = token
        }
    }

    suspend fun saveAuthDeviceUniqId(deviceUniqId: String) {
        dataStore.edit {
            it[authDeviceUniqId] = deviceUniqId
        }
    }

    suspend fun saveAuthDeviceDev(deviceDev: String) {
        dataStore.edit {
            it[authDevicedDev] = deviceDev
        }
    }


    // user
    fun getUserId() = runBlocking {
        dataStore.data.first()[userId]
    }

    fun getUserName() = runBlocking {
        dataStore.data.first()[userName]
    }

    fun getUserEmail() = runBlocking {
        dataStore.data.first()[userName]
    }

    fun getUserPassword() = runBlocking {
        dataStore.data.first()[userName]
    }

    fun getUserPhone() = runBlocking {
        dataStore.data.first()[userName]
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit {
            it[PreferencesManager.userId] = userId
        }
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit {
            it[PreferencesManager.userName] = userName
        }
    }

    suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit {
            it[PreferencesManager.userEmail] = userEmail
        }
    }

    suspend fun saveUserPassword(userPassword: String) {
        dataStore.edit {
            it[PreferencesManager.userPassword] = userPassword
        }
    }

    suspend fun savePhone(userPhone: String) {
        dataStore.edit {
            it[PreferencesManager.userPhone] = userPhone
        }
    }
}