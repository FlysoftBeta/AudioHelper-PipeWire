import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.posix.*

@OptIn(ExperimentalForeignApi::class)
fun main() {
    val process = popen(
        "pw-record --format s8 --properties '{ stream.capture.sink=true }' --quality 1 --channels 1 --rate 48000 -",
        "r"
    )
    if (process == NULL) throw RuntimeException("failed to spawn `pw-record`, check your PipeWire installation.")

    val buffer = ByteArray(1024)
    val maxDecibel = maxDecibel()
    while (true) {
        fgets(buffer.refTo(0), buffer.size, process) ?: break
        println((decibel(buffer).coerceAtLeast(0.0) / maxDecibel).coerceAtMost(1.0))
    }

    val status = pclose(process)
    if (status != 0) throw RuntimeException("`pw-record` failed with status $status")
}