package moadgara.base.extension

import android.os.Build
import android.os.Bundle
import com.google.gson.reflect.TypeToken
import moadgara.base.util.GsonProvider
import java.io.Serializable

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T> Bundle.putAny(key: String, value: T) {
    val gson = GsonProvider.get()
    val json = gson.toJson(value)
    putString(key, json)
}

inline fun <reified T> Bundle.getAny(key: String): T? {
    val json = getString(key)
    return if (json.isNullOrEmpty()) {
        null
    } else {
        val gson = GsonProvider.get()
        val type = object : TypeToken<T>() {}.type
        gson.fromJson<T>(json, type)
    }
}