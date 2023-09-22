import kotlin.math.log10
import kotlin.math.sqrt

private fun ByteArray.rms(): Double {
    val average = map { it * it }.average()
    val result = sqrt(average)
    return result
}

fun decibel(byteArray: ByteArray): Double {
    val rms = byteArray.rms()
    return if (rms <= 0) Double.NEGATIVE_INFINITY
    else 20 * log10(rms)
}

fun maxDecibel(): Double {
    return 20 * log10(Byte.MAX_VALUE.toDouble())
}
