package com.adi.magicspacex.utils.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.adi.magicspacex.R

val PoppinsRegular = FontFamily(Font(R.font.poppins_regular))
val PoppinsBold = FontFamily(Font(R.font.poppins_bold))

val Typography = Typography(
    PoppinsRegular,
    h1 = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    body1 = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)