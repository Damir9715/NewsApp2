package co.tredo.newsapp2

import java.io.Serializable

interface Router {

    suspend fun onPayload(payload: Payload)

    interface Payload : Serializable
}