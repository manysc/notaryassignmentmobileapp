package com.savasys.notaryassignmentmobileapp.data

import com.savasys.notaryassignmentmobileapp.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            if (username == "admin" && password == "admin") {
                val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                return Result.Success(fakeUser)
            }

            return Result.Failure("Invalid username/password, please try again!")
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}