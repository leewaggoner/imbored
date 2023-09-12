package com.wreckingball.imbored.repos

import com.wreckingball.imbored.domain.models.BoredActivityImage
import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.network.NetworkResponse
import com.wreckingball.imbored.network.PexelImageService
import com.wreckingball.imbored.network.models.PexelResponse
import com.wreckingball.imbored.network.toNetworkErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PexelImages(private val pexelImageService: PexelImageService) {
    private val imageMap = mutableMapOf<String, BoredActivityImage>()

    suspend fun getImageUrl(query: String) : ApiResult<BoredActivityImage> {
        var result: ApiResult<BoredActivityImage>?
        if (imageMap[query] == null) {
            //image has never been loaded
            result = callPexelApi(query).mapToApiResult()
            val url = result.data?.url
            if (url == null) {
                //no results exist yet for "busywork" -- try again
                result = callPexelApi("cleaning").mapToApiResult()
            }
            //mark this image as loaded... or not
            imageMap[query] = result.data as BoredActivityImage
        } else {
            //this image has already been loaded -- return the cached image data
            val imageData = imageMap[query]
            result = if (imageData == null) {
                imageMap.remove(query)
                ApiResult.Error("Unknown error!")
            } else {
                ApiResult.Success(imageData)
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

private fun NetworkResponse<PexelResponse>.mapToApiResult(): ApiResult<BoredActivityImage> =
    when (this) {
        is NetworkResponse.Success -> {
            if (data.photos.isNotEmpty()) {
                ApiResult.Success(data.mapToChooseActivityImage())
            } else {
                ApiResult.Error("Unknown network error!")
            }
        }
        is NetworkResponse.Error -> {
            ApiResult.Error("Error code ${code}: ${exception.localizedMessage}")
        }
    }

private fun PexelResponse.mapToChooseActivityImage(): BoredActivityImage {
    return BoredActivityImage(
        url = photos[0].src.portrait,
        photographer = photos[0].photographer,
        photographerUrl = photos[0].photographerUrl,
    )
}
