package com.hightech.ecommerce

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

object  PreferencesManager {

    private val context = EcommerceApplication.getApplicationContext()
    private val dataStore : DataStore<Preferences> =  context.createDataStore("preferences")

    private val isFirstLaunch =  booleanPreferencesKey("is_first_launch")

    // save data
    suspend fun saveIsFirstLaunch(isFirstLaunch : Boolean){
        dataStore.edit {
            it[this.isFirstLaunch] = isFirstLaunch
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
    fun getIsFirstLaunchLiveData(coroutineContext: CoroutineContext) =  getIsFirstLaunchAsync().asLiveData(coroutineContext)
}