package coil3.svg.internal

import coil3.PlatformContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import okio.BufferedSource
import okio.ByteString

internal fun BufferedSource.indexOf(
    bytes: ByteString,
    fromIndex: Long,
    toIndex: Long,
): Long {
    require(bytes.size > 0) { "bytes is empty" }

    val firstByte = bytes[0]
    val lastIndex = toIndex - bytes.size
    var currentIndex = fromIndex
    while (currentIndex < lastIndex) {
        currentIndex = indexOf(firstByte, currentIndex, lastIndex)
        if (currentIndex == -1L || rangeEquals(currentIndex, bytes)) {
            return currentIndex
        }
        currentIndex++
    }
    return -1
}

internal expect suspend inline fun <T> runInterruptible(
    context: CoroutineContext = EmptyCoroutineContext,
    noinline block: () -> T
): T

internal const val MIME_TYPE_SVG = "image/svg+xml"
internal const val SVG_DEFAULT_SIZE = 512

// Use a default 2KB value for the SVG's size in memory.
internal const val SVG_SIZE_BYTES = 2048L

internal expect val PlatformContext.density: Float
