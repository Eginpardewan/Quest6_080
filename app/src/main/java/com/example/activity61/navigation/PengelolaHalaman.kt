package com.example.activity61.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.activity61.ui.screen.DetailMahasiswa
import com.example.activity61.ui.screen.MahasiswaFormView
import com.example.activity61.ui.screen.RencanaStudiView
import com.example.activity61.ui.screen.SplashView
import com.example.activity61.ui.viewmodel.MahasiswaViewModel
import com.example.activity61.ui.viewmodel.RencanaStudiViewModel

enum class Halaman {
    Splash,
    Mahasiswa,
    Matakuliah,
    Tampil
}

@Composable
fun MahasiswaApp(
    modifier: Modifier = Modifier, // Pastikan ada default value untuk modifier
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudiViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val mahasiswaUiState = mahasiswaViewModel.mahasiswaUiState.collectAsState().value
    val krsUiState = krsViewModel.krsStateUi.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = modifier // Gunakan parameter modifier di sini
    ) {
        composable(route = Halaman.Splash.name) {
            SplashView(onMulaiButton = {
                navController.navigate(Halaman.Mahasiswa.name)
            })
        }
        composable(route = Halaman.Mahasiswa.name) {
            MahasiswaFormView(
                onSubmitButtonClicked = { mahasiswaData ->
                    mahasiswaViewModel.saveDataMahasiswa(mahasiswaData)
                    navController.navigate(Halaman.Matakuliah.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Matakuliah.name) {
            RencanaStudiView (
                mahasiswa = mahasiswaUiState,
                onSubmitButtonClicked = { krsData ->
                    krsViewModel.saveDataKRS(krsData)
                    navController.navigate(Halaman.Tampil.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Tampil.name) {
            DetailMahasiswa(
                mahasiswa = mahasiswaUiState,
                rencanaStudi = krsUiState,
                onBackButtonClicked = {
                    navController.navigate(Halaman.Splash.name)
                }
            )
        }
    }
}