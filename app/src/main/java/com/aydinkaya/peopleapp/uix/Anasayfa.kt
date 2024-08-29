package com.aydinkaya.peopleapp.uix

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aydinkaya.peopleapp.R
import com.example.peopleapp.entity.Kisiler
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController){
    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val kisilerListesi = remember { mutableStateListOf<Kisiler>() }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        val k1 = Kisiler(1,"Aydin","1111")
        val k2 = Kisiler(2,"Zeynep","2222")
        val k3 = Kisiler(3,"Beyza","3333")
        kisilerListesi.add(k1)
        kisilerListesi.add(k2)
        kisilerListesi.add(k3)
    }

    fun ara(aramaKelimesi:String){
        Log.e("Kişi Ara",aramaKelimesi)
    }

    fun sil(kisi_id:Int){
        Log.e("Kişi Sil",kisi_id.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (aramaYapiliyorMu.value){
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                ara(it)
                            },
                            label = { Text(text = "Ara")},
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedLabelColor = Color.Black,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    }else{
                        Text(text = "Kişiler")
                    }
                        },
                actions = {
                    if (aramaYapiliyorMu.value){
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = "")
                        }
                    }else{
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true
                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_search),
                                contentDescription = "")
                        }
                    }
                }
            )},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("kisiKayitSayfa") },
                content = {
                    Icon(painter = painterResource(id = R.drawable.baseline_add_), contentDescription = "")
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            items(
                count = kisilerListesi.size,
                itemContent = {//0,1,2
                    val kisi = kisilerListesi[it]
                    Card(modifier = Modifier.padding(all = 5.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable {
                                val kisiJson = Gson().toJson(kisi)
                                navController.navigate("kisiDetaySayfa/$kisiJson")
                            },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.padding(all = 10.dp)) {
                                Text(text = kisi.kisi_ad, fontSize = 20.sp)
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(text = kisi.kisi_tel)
                            }
                            IconButton(onClick = {
                                scope.launch {
                                    val sb = snackbarHostState.showSnackbar(
                                        message = "${kisi.kisi_ad} silinsin mi?",
                                        actionLabel = "EVET"
                                    )

                                    if(sb == SnackbarResult.ActionPerformed){
                                        sil(kisi.kisi_id)
                                    }
                                }
                            }) {
                                Icon(painter = painterResource(id = R.drawable.baseline_close_24),
                                    contentDescription = "", tint = Color.Gray)
                            }
                        }
                    }
                }
            )
        }
    }
}