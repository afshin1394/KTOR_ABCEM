package ir.irancell.application.configurations

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.httpsredirect.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }
//    install(HttpsRedirect) {
//        // The port to redirect to. By default 443, the default HTTPS port.
//        sslPort = 443
//        // 301 Moved Permanently, or 302 Found redirect.
//        permanentRedirect = true
//    }




//    install(SimpleCache) {
//        redisCache {
//            invalidateAt = 10.seconds
//            host = "localhost"
//            port = 6379
//        }
//    }
//    routing {
//        cacheOutput(2.seconds) {
//            get("/short") {
//                call.respond(Random.nextInt().toString())
//            }
//        }
//        cacheOutput {
//            get("/default") {
//                call.respond(Random.nextInt().toString())
//            }
//        }
//    }
}
