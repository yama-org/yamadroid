package com.yama.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.yama.R

// Set of Material typography styles to start with
val YamaFont = FontFamily(
    Font(R.font.kumbhsanslight, FontWeight.Light),
    Font(R.font.kumbhsansregular, FontWeight.Normal),
    Font(R.font.kumbhsansmedium, FontWeight.Medium),
    Font(R.font.kumbhsansbold, FontWeight.Bold)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = YamaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color(R.color.colorText)
    ),
    bodyLarge = TextStyle(
        fontFamily = YamaFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        color = Color(R.color.colorText)
    ),
    labelLarge = TextStyle(
        fontFamily = YamaFont,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        color = Color(R.color.colorText)
    ),
    headlineLarge = TextStyle(
        fontFamily = YamaFont,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        color = Color(R.color.white)
    )
)



