package app.sagnikmukherjee.permissionhandling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.sagnikmukherjee.permissionhandling.ui.theme.PermissionHandlingTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionHandlingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FeatureThatRequiresCameraPermission()
                }
            }
        }
    }
}

@Composable
fun Test() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val permissions = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        )
        var permissionsGranted by remember { mutableStateOf(false) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            permissionsGranted = results.all { it.value } // Check if all permissions are granted
        }

        if (permissionsGranted) {
            Text("Camera and Audio Permissions granted")
        } else {
            Button(
                onClick = {
                    launcher.launch(permissions.toTypedArray())
                }
            ) {
                Text("Get Permissions")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FeatureThatRequiresCameraPermission() {

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        Text("Camera permission Granted")
    } else {
        Column {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "The camera is important for this app. Please grant the permission."
            } else {
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}