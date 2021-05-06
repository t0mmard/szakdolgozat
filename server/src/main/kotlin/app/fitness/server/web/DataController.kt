package app.fitness.server.web

import app.fitness.server.model.*
import app.fitness.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

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
            return LoginResponse("ok",true, user)
        }
        else{
            return LoginResponse("nem létezik a felhasználó", false, null)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody authenticationData: AuthenticationData) : RegistrationResponse {
        if (userService.registerUser(authenticationData.username, authenticationData.password)){
            return RegistrationResponse("ok", true)
        }
        else{
            return  RegistrationResponse("sikertelen regisztráció", false)
        }

    }

    @PostMapping("/full_synchronization")
    fun fullSynchronization(@RequestBody fullSynchronizationData: FullSynchronizationData){
        return userService.fullSynchronization()
    }

    @PostMapping("/check_synchronized")
    fun checkSynchronized(@RequestBody synchronizationDateCheck : SynchronizationDateCheck) : SynchronizationDateCheck{
        return SynchronizationDateCheck(1, Date(),true)
    }

}