package moadgara.base.network

import android.os.Build
import java.util.Base64

object SecurityUtil {
    private lateinit var bytes: ByteArray
    fun decode(iteration: Int, key: String): String {
        while (!this::bytes.isInitialized) {
            try {
                bytes = key.toByteArray()
                for (i in 1..iteration) {
                    bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Base64.getMimeDecoder().decode(bytes)
                    } else {
                        android.util.Base64.decode(bytes, 0)
                    }
                }
            } catch (e: Exception) {
                decode(iteration, key)
            }
        }

        return String(bytes)
    }
}