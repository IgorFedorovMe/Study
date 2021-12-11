package me.igorfedorov.customviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.igorfedorov.customviewapp.feature.canvas.CanvasFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(
            android.R.id.content, CanvasFragment.newInstance()
        ).commit()
    }
}