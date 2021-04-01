package app.fitness.server.web

import app.fitness.server.model.AuthenticationData
import app.fitness.server.model.LoginResponse
import app.fitness.server.model.Test
import app.fitness.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DataController {

    @Autowired
    lateinit private var userService : UserService


    @GetMapping("/")
    fun index() : Test{
        return  Test("teszt", listOf("teszt2", "teszt3"))
    }

    @PostMapping("/check_login")
    fun tryLogin(@RequestBody authenticationData: AuthenticationData) : LoginResponse{
        val user = userService.getUserByEmailAndPassword(authenticationData.username,authenticationData.password)
        if (user != null){
            return LoginResponse("ok",true)
        }
        else{
            return LoginResponse("nem létezik a felhasználó", false)
        }

    }

}