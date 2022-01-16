package com.android.paging3demo.screens.common

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.paging3demo.R
import com.android.paging3demo.model.UnsplashImage
import com.android.paging3demo.model.Urls
import com.android.paging3demo.model.User
import com.android.paging3demo.model.UserLinks
import com.android.paging3demo.ui.theme.Shapes
import com.android.paging3demo.util.Constants.PARAMS_URL
import com.android.paging3demo.util.Constants.URL_USER

@ExperimentalCoilApi
@Composable
fun ListContent(
    items: LazyPagingItems<UnsplashImage>,
) {
    Log.d("Juan Error", items.loadState.toString())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { unsplashImage ->
                unsplashImage.id
            }
        ) { unsplashImage ->
            unsplashImage?.let { UnsplashItem(unsplashImage = it) }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun UnsplashItem(unsplashImage: UnsplashImage) {
    val painter = rememberImagePainter(data = unsplashImage.urls.regular) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$URL_USER${unsplashImage.urls.regular}$PARAMS_URL")
                )
                startActivity(context, browserIntent, null)
            }
            .clip(Shapes.small),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Unsplash image",
            contentScale = ContentScale.Crop,
        )

        UnsplashItemData(unsplashImage = unsplashImage)
    }
}

@Composable
fun UnsplashItemData(unsplashImage: UnsplashImage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.Black.copy(alpha = 0.5f))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = buildAnnotatedString {
                append("Photo by ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append(unsplashImage.user.username)
                }
                append(" on Unsplash")
            },
            modifier = Modifier.weight(3f),
            color = Color.White,
            fontSize = MaterialTheme.typography.caption.fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        LikeCounter(
            likes = "${unsplashImage.likes}",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LikeCounter(
    likes: String,
    modifier: Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            tint = Color.Red,
            contentDescription = "Heart icon",
        )

        Divider(modifier = Modifier.width(4.dp))

        Text(
            text = likes,
            color = Color.White,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun Preview() {
    UnsplashItem(
        unsplashImage = UnsplashImage(
            id = "1",
            urls = Urls(regular = "https://picsum.photos/seed/picsum/200/300"),
            likes = 100000,
            user = User(
                userLinks = UserLinks(
                    html = "laskjdflkjasldkf"
                ),
                username = "Juan"
            )
        )
    )

}