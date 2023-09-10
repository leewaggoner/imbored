package com.wreckingball.imbored.repos

import com.wreckingball.imbored.models.PexelResponse
import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.network.NetworkResponse
import com.wreckingball.imbored.network.PexelImageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PexelImages(private val pexelImageService: PexelImageService) {
    private val imageMap = mutableMapOf<String, String>()

    suspend fun getImageUrl(query: String) : ApiResult<String> {
        var result: ApiResult<String>?
        if (imageMap[query].isNullOrEmpty()) {
            var response = callPexelApi(query)
            result = response.mapToResult()
            if (result.data.isNullOrEmpty()) {
                response = callPexelApi("cleaning")
                result = response.mapToResult()
            }
            imageMap[query] = result.data ?: ""
        } else {
            val url = imageMap[query]
            result = if (url == null) {
                imageMap.remove(query)
                ApiResult.Error("Unknown error!")
            } else {
                ApiResult.Success(url)
            }
        }
        return result
    }

    private suspend fun callPexelApi(query: String) = withContext(Dispatchers.IO) {
        try {
            NetworkResponse.Success(pexelImageService.getImage(query))
        } catch (ex: HttpException) {
            ex.toNetworkErrorResponse()
        } catch (ex: Exception) {
            NetworkResponse.Error.UnknownNetworkError(ex)
        }
    }
}

private fun NetworkResponse<PexelResponse>.mapToResult(): ApiResult<String> =
    when (this) {
        is NetworkResponse.Success -> {
            if (data.photos.isNotEmpty()) {
                ApiResult.Success(data.photos[0].src.portrait)
            } else {
                ApiResult.Error("Unknown network error!")
            }
        }
        is NetworkResponse.Error -> {
            ApiResult.Error("Error code ${code}: ${exception.localizedMessage}")
        }
    }

private fun HttpException.toNetworkErrorResponse(): NetworkResponse<Nothing> =
    when (val code = code()) {
        400 -> NetworkResponse.Error.BadRequest(this, code)
        401,
        403 -> NetworkResponse.Error.Unauthorized(this, code)
        404 -> NetworkResponse.Error.NotFound(this, code)
        429 -> NetworkResponse.Error.TooManyRequests(this, code)
        in 400..499 -> NetworkResponse.Error.ApiError(this, code)
        in 500..599 -> NetworkResponse.Error.ServerError(this, code)
        else -> NetworkResponse.Error.UnknownNetworkError(this, code)
    }
