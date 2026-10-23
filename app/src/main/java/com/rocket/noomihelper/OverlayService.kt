package com.rocket.noomihelper
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
class OverlayService : Service() {
    private lateinit var wm: WindowManager
    private lateinit var view: FrameLayout
    private lateinit var marker: TextView
    private var pos = 0f; private var dir = 1
    private val h = Handler(Looper.getMainLooper()); private val speed = 12f 
    override fun onCreate() {
        super.onCreate()
        wm = getSystemService(WINDOW_SERVICE) as WindowManager
        view = FrameLayout(this).apply { setBackgroundColor(Color.argb(50, 0, 0, 0)) }
        val zone = TextView(this).apply {
            setBackgroundColor(Color.argb(150, 0, 100, 255))
            layoutParams = FrameLayout.LayoutParams(250, 150).apply { leftMargin = 200 }
        }
        marker = TextView(this).apply {
            setBackgroundColor(Color.RED)
            layoutParams = FrameLayout.LayoutParams(30, 150)
        }
        view.addView(zone); view.addView(marker)
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply { gravity = Gravity.TOP or Gravity.START; x = 50; y = 500 }
        wm.addView(view, params)
        h.post(object : Runnable {
            override fun run() {
                pos += dir * speed
                if (pos <= 0) { pos = 0f; dir = 1 }
                if (pos >= 450f) { pos = 450f; dir = -1 }
                marker.translationX = pos
                h.postDelayed(this, 16)
            }
        })
    }
    override fun onDestroy() { super.onDestroy(); h.removeCallbacksAndMessages(null); if (::view.isInitialized) wm.removeView(view) }
    override fun onBind(intent: Intent?): IBinder? = null
}
