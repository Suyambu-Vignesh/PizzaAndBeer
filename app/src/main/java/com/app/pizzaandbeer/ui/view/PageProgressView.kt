package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pizzaandbeer.R

@Composable
fun PageProgressView(
    modifier: Modifier = Modifier,
    progressText: String = stringResource(id = R.string.str_loading_default_text),
) {
    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = progressText,
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSecondary,
            style = MaterialTheme.typography.body1,
            fontStyle = FontStyle.Italic,
        )
    }
}

@Preview
@Composable
fun PreviewPageProgressView() {
    MaterialTheme {
        Surface {
            PageProgressView()
        }
    }
}
