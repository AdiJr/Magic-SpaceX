package com.adi.magicspacex.utils.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adi.magicspacex.R

/**
 * Banner with logo and app name.
 */
@Composable
fun LogoBanner(
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(60.dp),
            painter = painterResource(R.drawable.app_logo),
            contentScale = ContentScale.FillBounds,
            contentDescription = "app_logo_image"
        )

        HorizontalSpacer(width = 10.dp)

        Text(
            text = stringResource(id = R.string.app_name),
            color = textColor,
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun LogoBannerPreview() {
    LogoBanner()
}