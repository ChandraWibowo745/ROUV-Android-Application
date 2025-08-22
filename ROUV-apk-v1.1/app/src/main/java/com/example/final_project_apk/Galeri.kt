package com.example.final_project_apk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.final_project_apk.network.status.ServerHandler
import java.io.File
import androidx.core.net.toUri
import coil.decode.DecodeUtils.calculateInSampleSize
import com.example.final_project_apk.layout.Tab
import com.example.final_project_apk.network.DetectionViewModel
import com.example.final_project_apk.network.PreferenceManager
import com.example.final_project_apk.ui.theme.ArchivoBlackTypography
import com.example.final_project_apk.ui.theme.BlueDonker
import com.example.final_project_apk.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun GaleriPage(
    viewModel: ServerHandler,
    detectModel: DetectionViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val (ip, _, streamPort) = PreferenceManager.getActiveIpEntry(context)
        ?: Triple("192.168.1.99", "5000", "8000")
    val detectionUrl = "http://$ip:$streamPort/image_detected"

    // ViewModel data
    val latestBytes by detectModel.latestImage.collectAsState()
    var imageList by remember { mutableStateOf(emptyList<File>()) }

    // Initial load + polling
    LaunchedEffect(Unit) {
        imageList = loadImagesFromFolder(context)
        detectModel.startPolling(detectionUrl)
    }

    // Refresh on new image
    LaunchedEffect(latestBytes) {
        latestBytes?.let {
            imageList = loadImagesFromFolder(context)
        }
    }

    // UI
    Surface(modifier = Modifier.fillMaxSize(), color = BlueDonker) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Box(modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(284.dp)
                        .clip(RectangleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.big_ellipse_clip_content),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.25f))
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RectangleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.big_ellipse_clip_content),
                        contentDescription = "Deskripsi aksesibilitas",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart) // Paksa gambar ke bawah
                    )
                }
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.left_arrow_5737),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onBack()
                            }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "GALERI DETEKSI",
                        style = ArchivoBlackTypography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 10f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                        fontSize = 30.sp
                    )
                }
            }

            // Latest Image (if any)
            latestBytes?.let { bytes ->
                val bitmap = remember(bytes) {
                    decodeImage(bytes)
                }

                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Deteksi Terbaru",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-120).dp)
                            .height(200.dp)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }

            // Galeri Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(imageList, key = { it.name }) { image ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(image.toUri()),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

fun decodeImage(bytes: ByteArray): Bitmap? {
    return try {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
            inPreferredConfig = Bitmap.Config.RGB_565
        }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    } catch (e: Exception) {
        Log.e("DecodeImage", "Error decoding image: ${e.message}")
        null
    }
}

fun loadImagesFromFolder(context: Context): List<File> {
    val dir = File(context.getExternalFilesDir(null), "YourFolderName")
    return dir.listFiles()?.filter {
        it.extension.lowercase() in listOf("jpg", "jpeg", "png")
    }?.sortedByDescending { it.lastModified() } ?: emptyList()
}


