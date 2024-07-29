package com.abdts.musicplayerpractice

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.abdts.musicplayerpractice.data.service.VibAudioService
import com.abdts.musicplayerpractice.navigation.AppNavigation
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    private var isServiceRunning = false

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        setContent {
            MusicPlayerPracticeTheme {
                val permissionState = rememberPermissionState(permission = mediaPermission)
                PermissionHandler(
                    permissionState = permissionState,
                    onPermissionsGranted = {
                        val navController = rememberNavController()
                        AppNavigation(
                            navController = navController,
                        ) {
                            starMediaService()
                        }
                    },
                    onPermissionsDenied = {
                        PermissionDeniedUI(onRetry = {
                            permissionState.launchPermissionRequest()
                        }, onGoToSettings = {
                            openAppSettings()
                        })
                    }
                )
            }
        }
    }
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun PermissionHandler(
        permissionState: PermissionState,
        onPermissionsGranted: @Composable () -> Unit,
        onPermissionsDenied: @Composable () -> Unit
    ) {

        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }

        if (permissionState.status.isGranted) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

    @Composable
    private fun PermissionDeniedUI(
        onRetry: () -> Unit,
        onGoToSettings: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Accept reading media files permission!",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Button(onClick = onRetry) {
                    Text(text = "Ask for permission")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onGoToSettings) {
                    Text(text = "Open Settings")
                }
            }
        }
    }

    private fun starMediaService() {
        val intent = Intent(this, VibAudioService::class.java)
        if (!isServiceRunning) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        isServiceRunning = true
    }


    override fun onDestroy() {
        val intent = Intent(this, VibAudioService::class.java)
        stopService(intent)
        super.onDestroy()
    }
}


