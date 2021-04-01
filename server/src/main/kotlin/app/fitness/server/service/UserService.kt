package app.fitness.server.service

import app.fitness.server.model.User
import app.fitness.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService{

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        var user = username?.let{userRepository.getUserByEmail(it)}
        if (user == null){
            throw UsernameNotFoundException(String.format("'%s' nevű felhasználó nem létezik.", username))
        }
        return UserDetailsImpl(user)
    }

    fun getUserByEmailAndPassword(email : String, passwordHash : String) : User?{
        return userRepository.getUserByEmailAndPassword(email,passwordHash)
    }
}