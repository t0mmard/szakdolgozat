package app.fitness.server.repository

import app.fitness.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Long,> {

    fun getUserByEmail(email : String) : String

}