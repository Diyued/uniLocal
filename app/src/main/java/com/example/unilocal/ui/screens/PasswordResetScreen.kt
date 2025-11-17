package com.example.unilocal.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unilocal.R
import com.example.unilocal.ui.components.CustomButton
import com.example.unilocal.ui.components.CustomTextField
import com.example.unilocal.ui.components.OperationResultHandler
import com.example.unilocal.ui.viewmodel.UsersViewModel
import com.mapbox.common.toValue

@Composable
fun PasswordResetScreen(
    usersViewModel: UsersViewModel,
    onNavigateResetCode: (String) -> Unit,
    onLoginClick: () -> Unit
){

    var email by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val userResult by usersViewModel.userResult.collectAsState()


    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            Text(
                text = stringResource(id = R.string.txt_reset_password_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.txt_reset_password_subtitle),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = stringResource(id = R.string.txt_email),
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    }
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(
                text = stringResource(id = R.string.btn_send_email),
                onClick = {
                    usersViewModel.sendPasswordReset(email)
                }
            )

            OperationResultHandler(
                result = userResult,
                onSuccess = {
                    onLoginClick()
                    usersViewModel.resetOperationResult()
                },
                onFailure = {
                    usersViewModel.resetOperationResult()
                }
            )
        }
    }
}