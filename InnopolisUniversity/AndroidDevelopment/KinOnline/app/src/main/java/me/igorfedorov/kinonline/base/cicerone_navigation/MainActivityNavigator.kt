package me.igorfedorov.kinonline.base.cicerone_navigation

import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import me.igorfedorov.kinonline.R

class MainActivityNavigator(activity: AppCompatActivity) : AppNavigator(
    activity = activity,
    containerId = R.layout.activity_main
)