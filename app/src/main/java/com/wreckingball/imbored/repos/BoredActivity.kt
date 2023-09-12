package com.wreckingball.imbored.repos

import com.wreckingball.imbored.network.ApiResult
import com.wreckingball.imbored.network.BoredService
import com.wreckingball.imbored.network.NetworkResponse
import com.wreckingball.imbored.network.models.BoredActivityResponse
import com.wreckingball.imbored.network.toNetworkErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class BoredActivity(private val boredService: BoredService) {
    suspend fun getActivity(boredUrl: String, tryAgain: String): ApiResult<String> {
        val result = callBoredApi(boredUrl).mapTopApiResult(tryAgain)
        return result
    }

    private suspend fun callBoredApi(boredUrl: String) = withContext(Dispatchers.IO) {
        try {
            NetworkResponse.Success(boredService.getActivity(boredUrl))
        } catch (ex: HttpException) {
            ex.toNetworkErrorResponse()
        } catch (ex: Exception) {
            NetworkResponse.Error.UnknownNetworkError(ex)
        }
    }
}

private fun NetworkResponse<BoredActivityResponse>.mapTopApiResult(
    tryAgain: String
) : ApiResult<String> =
    when (this) {
        is NetworkResponse.Success -> {
            if (data.error.isNullOrEmpty()) {
                ApiResult.Success(data.activity)
            } else {
                ApiResult.Success("$tryAgain${data.error}")
            }
        }

        is NetworkResponse.Error -> {
            ApiResult.Error("Error code ${code}: ${exception.localizedMessage}")
        }
    }
