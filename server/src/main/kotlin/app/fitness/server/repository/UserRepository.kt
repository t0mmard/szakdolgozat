package app.fitness.server.repository

import app.fitness.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Long,> {

    @Query(nativeQuery = true, value = "select * from USERS where email = :email")
    fun getUserByEmail(email : String) : User

    @Query(nativeQuery = true, value = "select * from USERS where email = :email and password_hash = :passwordHash")
    fun getUserByEmailAndPassword(email : String, passwordHash : String) : User?

}