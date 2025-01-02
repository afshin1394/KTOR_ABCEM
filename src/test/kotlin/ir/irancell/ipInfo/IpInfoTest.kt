package ir.irancell.ipInfo

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class IpInfoTest {

    // Assuming the extractIpInfo function is part of a handler class.
    // If it's a top-level function or part of a different class, adjust accordingly.
    class IpInfoRoutesHandler {
        suspend fun extractIpInfo(call: ApplicationCall): String? {
            // Check 'X-Forwarded-For' header first
            val xForwardedFor = call.request.headers["X-Forwarded-For"]
            if (!xForwardedFor.isNullOrBlank()) {
                // X-Forwarded-For can contain multiple IPs, the first is the client
                return xForwardedFor.split(",").firstOrNull()?.trim()
            }

            // Fallback to 'Forwarded' header
            val forwarded = call.request.headers["Forwarded"]
            if (!forwarded.isNullOrBlank()) {
                // Example: Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43
                val forPart = forwarded.split(";")
                    .firstOrNull { it.trim().startsWith("for=") }
                    ?.substringAfter("for=")
                    ?.trim('"') // Remove quotes if present
                return forPart
            }

            // Fallback to remote host
            return call.request.origin.remoteHost
        }
    }

    private val handler = IpInfoRoutesHandler()

    @Test
    fun `extractIpInfo returns IP from Forwarded header with quotes`() = runBlocking {
        // Arrange
        val clientIp = "203.0.113.195"
        val forwardedHeaderValue = """for="$clientIp";proto=http;by=198.51.100.1"""

        // Mock ApplicationCall
        val call = mockk<ApplicationCall>()

        // Mock RequestApplicationCall
        val request = mockk<ApplicationRequest>()

        // Mock RequestConnectionPoint
        val origin = mockk<RequestConnectionPoint>()

        // Create a real Headers instance with the Forwarded header
        val headers = Headers.build {
            append("Forwarded", forwardedHeaderValue)
        }

        // Set up the mocks
        coEvery { call.request } returns request
        coEvery { request.headers } returns headers
        coEvery { request.origin } returns origin
        coEvery { origin.remoteHost } returns "198.51.100.1" // Should not be used in this test

        // Act
        val extractedIp = handler.extractIpInfo(call)
        println(extractedIp)
        println(clientIp)
        // Assert
        assertEquals(clientIp, extractedIp)
    }
}
