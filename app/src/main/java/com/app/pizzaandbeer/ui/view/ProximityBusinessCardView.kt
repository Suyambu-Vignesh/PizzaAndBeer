package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.ui.model.ProximityServiceBusinessState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProximityBusinessCardView(
    state: ProximityServiceBusinessState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
    ) {
        val imagePainter =
            state.imageUrl?.let {
                // todo imageUrl doesn't have width and height option, It will be risky to load
                // the images can crash in lower mem device. We need to find ways to request image based
                // on device res.
                rememberAsyncImagePainter(state.imageUrl)
            } ?: painterResource(R.drawable.ic_restaurant_24)

        Row(modifier = modifier.padding(all = 8.dp)) {
            Image(
                painter = imagePainter,
                modifier =
                    modifier
                        .height(100.dp)
                        .width(100.dp),
                contentDescription = "alt",
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = modifier.width(8.dp))

            Column {
                Text(
                    text = state.name,
                    color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.subtitle1,
                )

                Spacer(modifier = modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = state.price ?: "",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.subtitle1,
                    )

                    Spacer(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )

                    if (state.ratingAsString != null) {
                        Text(
                            text = state.ratingAsString,
                            color = MaterialTheme.colors.onSecondary,
                            style = MaterialTheme.typography.subtitle1,
                        )

                        Image(
                            painter = painterResource(R.drawable.ic_rating_star),
                            contentDescription = "alt",
                        )
                    }
                }

                if (state.categories != null) {
                    Spacer(modifier = modifier.height(4.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        state.categories.forEach {
                            Text(
                                text = it,
                                color = MaterialTheme.colors.onSecondary,
                                style = MaterialTheme.typography.caption,
                                fontStyle = FontStyle.Italic,
                                modifier = modifier.wrapContentHeight(),
                            )
                        }
                    }
                }

                if (state.displayAddress != null) {
                    Spacer(modifier = modifier.height(4.dp))
                    FlowColumn {
                        state.displayAddress.forEach {
                            Text(
                                text = it,
                                color = MaterialTheme.colors.onSecondary,
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProximityBusinessCardView() {
    MaterialTheme {
        Surface {
            ProximityBusinessCardView(
                state =
                    ProximityServiceBusinessState(
                        id = "id1",
                        name = "Pizza Corner1",
                        imageUrl = "https://s3-media3.fl.yelpcdn.com/bphoto/YP_8Tm4LXcI2FqTfZuxvAA/o.jpg?width=50&height=50",
                        reviewCountAsString = "50",
                        categories = listOf("eatery", "bar", "rest area", "kids", "park"),
                        ratingAsString = "4.5",
                        price = "$$",
                        displayAddress = listOf("Murphy avenue", "sunnyvale"),
                    ),
            )
        }
    }
}
