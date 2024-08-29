package com.example.peopleapp.uix


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.aydinkaya.peopleapp.uix.Anasayfa
import com.aydinkaya.peopleapp.uix.KisiDetaySayfa
import com.example.peopleapp.entity.Kisiler

@Composable
fun SayfaGecisleri(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "anasayfa"){
        composable("anasayfa"){
            Anasayfa(navController)
        }
        composable("kisiKayitSayfa"){
            KisiKayitSayfa()
        }
        composable(
            "kisiDetaySayfa/{kisi}",
            arguments = listOf(
                navArgument("kisi") { type = NavType.StringType }
            )
        ){
            val json = it.arguments?.getString("kisi")
            val nesne = Gson().fromJson(json, Kisiler::class.java)
            KisiDetaySayfa(nesne)
        }
    }

}