package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.ui.model.ErrorState
import com.app.pizzaandbeer.ui.model.ErrorType

@Composable
fun ErrorView(
    state: ErrorState,
    modifier: Modifier = Modifier,
    onClick: (captionId: Int) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.height(16.dp))

        Image(
            painter = painterResource(state.drawableRes),
            contentDescription = "Contact profile picture",
            modifier = modifier,
        )

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = stringResource(state.titleId),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = stringResource(state.additionalMessageId),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.caption,
        )

        if (state.buttonCaptionId != null) {
            Spacer(modifier = modifier.height(16.dp))
            Button(
                onClick = {
                    onClick(state.buttonCaptionId)
                },
            ) {
                Text(
                    text = stringResource(state.buttonCaptionId),
                    textAlign = TextAlign.Center,
                    modifier = modifier,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.caption,
                )
            }
        }

        Spacer(modifier = modifier.height(16.dp))
    }
}

@Preview
@Composable
fun PreviewClientErrorMessage() {
    MaterialTheme {
        Surface {
            ErrorView(
                ErrorState(
                    ErrorType.TYPE_CLIENT_ERROR,
                    R.drawable.ic_launcher_foreground,
                    R.string.str_default_error_title,
                    R.string.str_default_error_detail,
                    null,
                ),
            ) {
            }
        }
    }
}

@Preview
@Composable
fun PreviewServerErrorMessage() {
    MaterialTheme {
        Surface {
            ErrorView(
                ErrorState(
                    ErrorType.TYPE_CLIENT_ERROR,
                    R.drawable.ic_launcher_foreground,
                    R.string.str_default_error_title,
                    R.string.str_default_error_detail,
                    R.string.str_btn_retry,
                ),
            ) {
            }
        }
    }
}
