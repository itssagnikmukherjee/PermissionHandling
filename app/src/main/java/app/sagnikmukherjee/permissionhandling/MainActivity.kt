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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionHandlingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    test()
                }
            }
        }
    }
}

@Composable
fun test() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val permission = listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        )
        var isGranted by remember { mutableStateOf(false) }
        val launch = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(), onResult = {
                isGranted = it
            }
        )
        if (isGranted) {
            Text("Camera Permission granted")
        } else {
            Button(
                onClick = {
                    launch.launch(permission)
                }
            ) {
                Text("Get Permission")
            }
        }
    }
}