package app.fitness.server.web

import app.fitness.server.model.Test
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DataController {

    @GetMapping("/")
    fun index() : Test{
        return  Test("működik a fogat", listOf("megez", "megez"))
    }

    @GetMapping("/login/{email}")
    fun tryLogin() : String{
        return "nothing"
    }

}