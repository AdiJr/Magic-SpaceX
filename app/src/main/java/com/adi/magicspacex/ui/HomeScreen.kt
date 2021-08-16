package com.adi.magicspacex.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adi.magicspacex.models.CompanyData
import com.adi.magicspacex.viewmodels.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val companyData: CompanyData? by homeViewModel.companyData.observeAsState()
    companyData?.let { HomeScreenData(it) }
}

@Composable
fun HomeScreenData(companyData: CompanyData) {
    Text(
        text = companyData.founder,
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize(align = Alignment.Center),
        style = TextStyle(color = Color.White, fontSize = 25.sp)
    )

}