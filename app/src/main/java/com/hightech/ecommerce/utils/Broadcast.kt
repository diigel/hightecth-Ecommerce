package com.hightech.ecommerce.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Broadcast private constructor(private val coroutineScope: CoroutineScope) {

    /**
     * Payload of broadcast
     * @param key is your identifier broadcast
     * @param data is your payload
     */
    data class BroadcastPayload(val key: String, val data: Any?)

    companion object {
        private var broadcast: Broadcast? = null

        /**
         * @param coroutineScope:
         * Use scope for your broadcast
         */
        fun with(coroutineScope: CoroutineScope): Broadcast {
            if (broadcast == null) broadcast = Broadcast(coroutineScope)
            return broadcast!!
        }
    }

    private val channel = BroadcastChannel<BroadcastPayload>(1)

    private suspend fun postData(key: String, data: Any? = null) {
        val broadcastPayload = BroadcastPayload(key, data)
        channel.send(broadcastPayload)
    }

    @FlowPreview
    @InternalCoroutinesApi
    private suspend fun observerData(payload: (key: String, data: Any?) -> Unit) {
        channel.asFlow().collect {
            payload.invoke(it.key, it.data)
        }
    }

    private suspend fun observerData(key: String, payload: (data: Any?) -> Unit) {
        channel.asFlow().collect {
            if (key == it.key) payload.invoke(it.data)
        }
    }

    /**
     * Send your data as observable
     * @param key: identifier your observable
     * @param data: data carried when your send observable
     */
    fun post(key: String, data: Any? = null) {
        coroutineScope.launch {
            postData(key, data)
        }
    }

    /**
     * Observer your data
     * @param payload: function to build lambda (higher function), data arrived here
     */
    @InternalCoroutinesApi
    fun observer(payload: (key: String, data: Any?) -> Unit) {
        coroutineScope.launch {
            observerData(payload)
        }
    }

    /**
     * Observer your data
     * @param key: observing with your key
     * @param payload: function to build lambda (higher function), data arrived here
     */
    fun observer(key: String, payload: (data: Any?) -> Unit) {
        coroutineScope.launch {
            observerData(key, payload)
        }
    }
}