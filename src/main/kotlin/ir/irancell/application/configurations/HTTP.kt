package ir.irancell.application.configurations

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*


fun Application.configureHTTP() {

    install(CORS) {
        anyHost() // Allows all origins (for development). Use cautiously in production!
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Options)
        allowHeader("Content-Type")
        allowHeader("Authorization")
        allowCredentials = true // If cookies or credentials are needed
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
