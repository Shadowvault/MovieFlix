package com.shadowvault.core.data

import android.os.Build
import com.shadowvault.core.domain.util.DataError
import com.shadowvault.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer
import timber.log.Timber
import java.net.SocketTimeoutException

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
    headers: Map<String, String> = mapOf()
): Result<Response, DataError> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }

            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.postForm(
    route: String,
    body: Request
): Result<Response, DataError> {
    return safeCall {
        val serializer = Json.serializersModule.serializer<Request>()
        val jsonString = Json.encodeToString(serializer, body)
        val jsonObject = Json.parseToJsonElement(jsonString).jsonObject

        val formParameters = Parameters.build {
            for ((key, value) in jsonObject) {
                append(key, value.jsonPrimitive.content)
            }
        }
        submitForm(
            url = BuildConfig.BASE_URL + "/$route",
            formParameters = formParameters
        )
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
    headers: Map<String, String> = mapOf()
): Result<Response, DataError> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
            header("X-MobileApp-OS", BuildConfig.OS)
            header("X-Smartphone-Brand", Build.BRAND)
            header("X-Smartphone-Model", Build.MODEL)
            header("X-Smartphone-OSVersion", Build.VERSION.RELEASE)
            header("X-Api-Version", "1.0")
            header("X-MobileApp-Version", "1")

            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    body: Request
): Result<Response, DataError> {
    return safeCall {
        put {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

@Suppress("TooGenericExceptionCaught", "ReturnCount")
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        Timber.e(e)
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: HttpRequestTimeoutException) {
        Timber.e(e)
        return Result.Error(DataError.Network.SERVER_REQUEST_TIMEOUT)
    } catch (e: ConnectTimeoutException) {
        Timber.e(e)
        return Result.Error(DataError.Network.CONNECTION_TIMEOUT)
    } catch (e: SocketTimeoutException) {
        Timber.e(e)
        return Result.Error(DataError.Network.SOCKET_TIMEOUT)
    } catch (e: SerializationException) {
        Timber.e(e)
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Timber.e(e)
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

@Suppress("MagicNumber")
suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Remote.Http.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.SERVER_REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Remote.Http.CONFLICT)
        413 -> Result.Error(DataError.Remote.Http.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Remote.Http.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.Http.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    val baseUrl = BuildConfig.BASE_URL
    return when {
        route.contains(baseUrl) -> route
        route.startsWith("/") -> baseUrl + route
        else -> "$baseUrl/$route"
    }
}
