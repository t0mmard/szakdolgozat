package app.fitness.server.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId : Long,
    val email : String,
    val lastName: String,
    val passwordHash: String,
    val passwordSaved: Boolean,
    val goalWeight: Short?,
    val loggedIn: Boolean,
    val synchronizationDate: Date
)