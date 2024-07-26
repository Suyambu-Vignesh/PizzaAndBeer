package com.app.pizzaandbeer.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.data.error.AppNetworkException
import com.app.pizzaandbeer.data.error.ClientRequestErrorException
import com.app.pizzaandbeer.data.error.EmptyBusinessInformationException
import com.app.pizzaandbeer.data.error.NoLocationPermissionException
import com.app.pizzaandbeer.data.error.ServerErrorException
import com.app.pizzaandbeer.data.error.TooManyRequestException

data class ErrorState(
    val errorType: ErrorType,
    @DrawableRes val drawableRes: Int,
    @StringRes val titleId: Int,
    @StringRes val additionalMessageId: Int,
    @StringRes val buttonCaptionId: Int?,
) {
    constructor(throwable: Throwable) : this(
        errorType = throwable.getErrorType(),
        drawableRes = throwable.getDrawableRes(),
        titleId = throwable.getTitleId(),
        additionalMessageId = throwable.getMessageId(),
        buttonCaptionId = throwable.getButtonCaptionId(),
    )
}

// todo Retry can add unwanted server traffic, There are different cases need to be considered
// like exponential back off in case of server and too many request, ttl
private fun Throwable.getButtonCaptionId(): Int? {
    return when {
        (this is ServerErrorException || this is AppNetworkException || this is TooManyRequestException) -> {
            R.string.str_btn_retry
        }

        this is NoLocationPermissionException -> {
            R.string.str_btn_location_permission
        }

        else -> {
            null
        }
    }
}

@StringRes
private fun Throwable.getTitleId(): Int {
    return when (this) {
        is AppNetworkException -> {
            R.string.str_no_network_error_title
        }

        is NoLocationPermissionException -> {
            R.string.str_location_error_title
        }

        is EmptyBusinessInformationException -> {
            R.string.str_no_result_error_title
        }

        else -> {
            R.string.str_default_error_title
        }
    }
}

@StringRes
private fun Throwable.getMessageId(): Int {
    return when (this) {
        is AppNetworkException -> {
            R.string.str_no_network_error_detail
        }

        is NoLocationPermissionException -> {
            R.string.str_location_error_detail
        }

        is EmptyBusinessInformationException -> {
            R.string.str_no_result_error_detail
        }

        else -> {
            R.string.str_default_error_detail
        }
    }
}

@DrawableRes
private fun Throwable.getDrawableRes(): Int {
    return if (this is AppNetworkException) {
        R.drawable.ic_no_network
    } else {
        // todo add more case based on the error type
        R.drawable.ic_generic_error
    }
}

enum class ErrorType {
    TYPE_NO_LOCATION_INFO,
    TYPE_NO_RESULT,
    TYPE_NO_NETWORK,
    TYPE_CLIENT_ERROR,
    TYPE_SERVER_ERROR,
    TYPE_TOO_MANY_REQUEST,
    TYPE_TAIL_END_ERROR,
}

private fun Throwable.getErrorType(): ErrorType {
    return when (this) {
        is AppNetworkException -> {
            ErrorType.TYPE_NO_NETWORK
        }

        is NoLocationPermissionException -> {
            ErrorType.TYPE_NO_LOCATION_INFO
        }

        is ServerErrorException -> {
            ErrorType.TYPE_SERVER_ERROR
        }

        is ClientRequestErrorException -> {
            ErrorType.TYPE_CLIENT_ERROR
        }

        is EmptyBusinessInformationException -> {
            ErrorType.TYPE_NO_RESULT
        }

        is TooManyRequestException -> {
            ErrorType.TYPE_TOO_MANY_REQUEST
        }

        else -> {
            ErrorType.TYPE_TAIL_END_ERROR
        }
    }
}
