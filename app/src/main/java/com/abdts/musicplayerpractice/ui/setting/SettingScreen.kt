package com.abdts.musicplayerpractice.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // General Settings Section
        Text(
            text = "General Settings",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        SettingItem(title = "Playback Quality")
        SettingItem(title = "Notifications")
        SettingItem(title = "Download Settings")

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Policies Section
        Text(
            text = "Policies",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        SettingItem(title = "Privacy Policy")
        SettingItem(title = "Terms of Service")
        SettingItem(title = "Cookie Policy")

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // About Section
        Text(
            text = "About",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        SettingItem(title = "App Version")
        SettingItem(title = "Developer Info")
        SettingItem(title = "Open Source Licenses")
    }
}

@Composable
fun SettingItem(title: String) {
    Text(
        text = title,
        fontSize = 15.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /*TODO*/ }
            .padding(vertical = 12.dp)
    )
}