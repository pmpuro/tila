package io.tila

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    fun getName(p: Platform): String {
        return p.name
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()
