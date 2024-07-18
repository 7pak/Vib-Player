package com.abdts.musicplayerpractice

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.abdts.musicplayerpractice.navigation.AppNavigation
import com.abdts.musicplayerpractice.ui.SharedViewModel
import com.abdts.musicplayerpractice.ui.theme.MusicPlayerPracticeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        setContent {
            val permission = rememberMultiplePermissionsState(
                permissions = listOf(
                    mediaPermission
                )
            )
            LaunchedEffect(Unit) {
                permission.launchMultiplePermissionRequest()
            }

            if (permission.allPermissionsGranted) {
                val navController = rememberNavController()
                val sharedViewModel = getViewModel<SharedViewModel>()
                MusicPlayerPracticeTheme {
                    AppNavigation(
                        navHostController = navController,
                        sharedViewModel = sharedViewModel
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Accept reading file permission!",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }
            }
        }
    }
}
