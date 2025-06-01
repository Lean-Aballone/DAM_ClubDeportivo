package com.example.clubdeportivo

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.Button

abstract class Utils() {

    companion object {
        fun gradientPostProcessing (button: Button) {
            button.post {
                val width = button.width.toFloat()
                val textShader = LinearGradient(
                    0f, 0f, width, 0f,
                    intArrayOf(
                        0xFF00FFFF.toInt(), // #0FF
                        0xFFFF00FF.toInt() // #F0F
                    ),
                    null,
                    Shader.TileMode.CLAMP
                )
                button.paint.shader = textShader
                button.invalidate()
            }
        }
    }
}