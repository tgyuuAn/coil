package coil3.decode

import coil3.ImageLoader
import coil3.fetch.DataUriFetcher
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import coil3.test.utils.RobolectricTest
import coil3.test.utils.context
import coil3.toUri
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest

class DataUriFetcherTest : RobolectricTest() {
    private val fetcher = DataUriFetcher.Factory()

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun supported() = runTest {
        val uri = "data:image/png;base64,$IMAGE_DATA".toUri()
        val fetcher = assertNotNull(fetcher.create(uri, Options(context), ImageLoader(context)))

        val result = assertIs<SourceFetchResult>(fetcher.fetch())

        assertEquals("image/png", result.mimeType)
        assertEquals(
            expected = Base64.decode(IMAGE_DATA).joinToString(),
            actual = result.source.use { it.source().readByteArray() }.joinToString(),
        )
    }
}

private const val IMAGE_DATA = "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"
