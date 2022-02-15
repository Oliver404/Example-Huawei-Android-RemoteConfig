package com.oliverbotello.eha_remoteconfig

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.huawei.agconnect.remoteconfig.AGConnectConfig
import com.oliverbotello.eha_remoteconfig.utils.showMessage
import android.os.Handler


class MainActivity : AppCompatActivity() {
    private val config: AGConnectConfig = AGConnectConfig.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRemoteConfig()
    }

    // Class methods
    private fun setRemoteConfig() {
        config.clearAll()
        config.fetch().addOnSuccessListener {
            showMessage(applicationContext, "addOnSuccessListener")
            config.apply(it)
        }.addOnFailureListener {
            showMessage(applicationContext, "addOnFailureListener")
            setDefaultRemoteConfigByXML()
        }.addOnCompleteListener {
            showMessage(applicationContext, "addOnCompleteListener")
            initView()
        }
    }

    private fun setDefaultRemoteConfigByXML() {
        config.applyDefault(R.xml.remote_config)
    }

    private fun setDefaultRemoteConfigProgramatically() {
        val map = mutableMapOf<String,Any>()
        map["primary_color"] = "#00FFFF"
        map["title"] = "Title default remote config programatically"
        map["text_size"] = 24

        config.applyDefault(map)
    }

    private fun initView() {
        val titleView = findViewById<TextView>(R.id.txtVw_Title)
        titleView.text = config.getValueAsString("title")
        
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getValueAsDouble("text_size").toFloat())
        titleView.setTextColor(Color.parseColor(config.getValueAsString("primary_color")))
        findViewById<TextView>(R.id.btn_start_animation).setOnClickListener{
            createLayouts()
        }
    }

    private fun createLayouts() {

        val lyta = findViewById<LinearLayout>(R.id.lyt_animation)

        for (i in 1 until 11) {
            val lnyt = LinearLayout(this)
            val params = LinearLayout.LayoutParams(50, 50)
            lnyt.layoutParams = params
            lnyt.setBackgroundColor(Color.RED)
            val handler = Handler()
            handler.postDelayed(
                Runnable {
                    lyta.addView(lnyt)
                    val animation = ObjectAnimator.ofFloat(lnyt, "translationX", 1000f).apply {
                    duration = 1000
                    start()
                } },
                1000 * i.toLong()
            )
        }
    }
}