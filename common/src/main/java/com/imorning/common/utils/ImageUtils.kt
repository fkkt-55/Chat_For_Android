package com.imorning.common.utils

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import com.imorning.chat.App
import com.imorning.common.BuildConfig
import java.io.InputStream


class ImageUtils {

    companion object {
        private const val TAG = "ImageUtils"

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ImageUtils()
        }
    }

    fun getDrawable(inputStream: InputStream?): Drawable {
        val drawable = BitmapDrawable.createFromResourceStream(
            App.getContext().resources,
            null,
            inputStream,
            null
        )
        return drawable
    }

}