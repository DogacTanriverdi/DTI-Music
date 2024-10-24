package com.dogactanriverdi.dtimusic.presentation.home.components

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckAndRequestPermission(
    permissionGranted: @Composable () -> Unit
) {
    val context = LocalContext.current

    val audioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(android.Manifest.permission.READ_MEDIA_AUDIO)
    } else {
        listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionState = rememberMultiplePermissionsState(permissions = audioPermission)
    var isPermissionDeniedPermanently by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = permissionState.allPermissionsGranted) {
        if (!permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    isPermissionDeniedPermanently =
        !permissionState.shouldShowRationale && permissionState.revokedPermissions.isNotEmpty()

    when {

        permissionState.allPermissionsGranted -> {
            permissionGranted()
        }

        permissionState.shouldShowRationale && permissionState.revokedPermissions.isNotEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "We need permission to access audio files.",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { permissionState.launchMultiplePermissionRequest() }
                ) {
                    Text(text = "Request Permission Again")
                }
            }
        }

        isPermissionDeniedPermanently && !permissionState.shouldShowRationale -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "You have permanently denied the permission. Please enable it in settings.",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = "Go to Settings")
                }
            }
        }
    }
}