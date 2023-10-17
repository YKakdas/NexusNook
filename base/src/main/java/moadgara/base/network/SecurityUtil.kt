package moadgara.base.network

import android.os.Build
import java.util.Base64

object SecurityUtil {
    private lateinit var bytes: ByteArray
    fun decode(iteration: Int, key: String): String {
        bytes = key.toByteArray()
        for (i in 1..iteration) {
            bytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(bytes)
            } else {
                android.util.Base64.decode(bytes, 0)
            }
        }
        return if (this::bytes.isInitialized) String(bytes) else ""
    }
}