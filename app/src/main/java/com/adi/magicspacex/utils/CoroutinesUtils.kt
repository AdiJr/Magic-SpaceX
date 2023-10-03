package com.adi.magicspacex.utils

import kotlinx.coroutines.CancellationException

/**
 * Rethrow [CancellationException] and provide callback for handling exceptions.
 */
inline fun <T> cancellationAwareTryCatch(
    tryBlock: () -> T,
    catchBlock: (Exception) -> T,
) = try {
    tryBlock()
} catch (cancellation: CancellationException) {
    catchBlock(cancellation)
    throw cancellation
} catch (ex: Exception) {
    catchBlock(ex)
}