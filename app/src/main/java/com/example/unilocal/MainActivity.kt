package com.example.unilocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unilocal.ui.theme.UniLocalTheme
import com.example.unilocal.ui.nav.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent (
            content = {
                UniLocalTheme {
                    Navigation()
                }
            }
        )
    }
}