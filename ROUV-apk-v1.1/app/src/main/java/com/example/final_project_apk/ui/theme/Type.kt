package com.example.final_project_apk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_project_apk.R

val Archivo = FontFamily(
    Font(R.font.archivo_regular, FontWeight.Normal),
    Font(R.font.archivo_bold, FontWeight.Bold),
    Font(R.font.archivo_italic, FontWeight.Normal)
)

val ArchivoBlack = FontFamily(
    Font(R.font.archivo_black, FontWeight.Normal),
    Font(R.font.archivo_blackitalic, FontWeight.Normal)
)

// Set of Material typography styles to start with
val ArchivoTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),

    labelSmall = TextStyle( // Style custom untuk teks italic
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic, // ← Aktifkan italic
        fontSize = 18.sp
    )
)

val ArchivoBlackTypography = Typography(

    bodyLarge = TextStyle(
        fontFamily = ArchivoBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    labelSmall = TextStyle( // Style custom untuk teks italic
        fontFamily = ArchivoBlack,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic, // ← Aktifkan italic
        fontSize = 18.sp
    )
)



