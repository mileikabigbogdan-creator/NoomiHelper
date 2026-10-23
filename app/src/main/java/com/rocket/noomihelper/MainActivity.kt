package com.rocket.noomihelper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(50, 100, 50, 50) }
        val btn = Button(this).apply {
            text = "ЗАПУСТИТЬ ОВЕРЛЕЙ"
            textSize = 18f
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
                } else {
                    startService(Intent(this@MainActivity, OverlayService::class.java))
                    finish()
                }
            }
        }
        layout.addView(btn)
        setContentView(layout)
    }
}
