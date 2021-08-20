package com.adi.magicspacex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.viewmodels.LaunchDetailsViewModel

@Composable
fun LaunchScreen(detailsViewModel: LaunchDetailsViewModel = viewModel()) {
    val launch: Launch? by detailsViewModel.launch.observeAsState()

}