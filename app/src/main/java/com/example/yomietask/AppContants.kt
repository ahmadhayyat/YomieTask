package com.example.yomietask

import android.util.TypedValue
import android.webkit.MimeTypeMap

class AppContants {


    companion object {
        val MIME_TYPE_IMG = "image/jpeg"
        val MIME_TYPE_VIDEO = "video/mp4"
        val MIME_TYPE_WEB = "application/x-msdos-program"

        fun getMimeType(resId: Int): String? {
            val value = TypedValue()
            App.applicationContext().resources.getValue(resId, value, true);
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(value.string.toString())
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }
    }
}