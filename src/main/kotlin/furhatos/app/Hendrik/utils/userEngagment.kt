package furhatos.app.myadvancedskill.utils

import furhatos.app.myadvancedskill.setting.*
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.Furhat.Companion.dialogHistory
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.users
import furhatos.gestures.Gestures
import furhatos.records.Location
import furhatos.records.User
import furhatos.skills.UserManager
import kotlin.random.Random.Default.nextBoolean


var userThatLastSpoke = if (dialogHistory.responses.isNotEmpty()) dialogHistory.responses.last().response.userId else "" // TODO replaced by UserManager.lastUserThatSpoke??
var lastAttendNobodyTimeStamp = System.currentTimeMillis()

val UserManager.usersAttendingFurhat: List<User> get() = this.all.filter { it.isAttendingFurhat }
val UserManager.userClosestToFurhat: User get() = this.userClosestToPosition(Location.ORIGIN)
val UserManager.smilingUsers: List<User> get() = this.all.filter { it.isSmiling }
val UserManager.lastUserThatSpoke: User get() = this.getUser(dialogHistory.responses.last().response.userId) // TODO verify this
fun UserManager.nextMostEngagedUser(): User {
    // Signs of engagement
    // 1. Looks at the robot
    // 2. User standing closest
    when (count) {
        0 -> return random
        1 -> return random // not really random when only one user =)
        else -> when (usersAttendingFurhat.count()) {
            0 -> return userClosestToFurhat
            1 -> return usersAttendingFurhat[0]
            in 2..MAX_NUMBER_OF_USERS -> {
                val userCandidates = usersAttendingFurhat.sortedBy { it.head.location.distance(Location.ORIGIN) } // TODO verify this return the closest user
                // don't return a user that we are already attending.
                for (candidate in userCandidates) if (candidate != current) return candidate
            }
            else -> return random
        }
    }
    return random
}

fun UserManager.isAnyUserSmiling(): Boolean {
    for (user in this.all) {
        if (user.isSmiling) {
            return true
            break
        }
    }
    return false
}

/** Function to store our relation with the user on the user object
 * If reidentifiation works as intended we will recognise the user as a "friend". */
fun FlowControlRunner.rememberUser() {
    // First double check we are attending a user, otherwise there is no user to remember
    if (furhat.isAttending(users.current)) {
        // remember user as "friend"
        users.current.relation = "friend"
    }
}


fun Furhat.isAttended(): Boolean {
    for (user in users.all) {
        if (user.isAttendingFurhat()) {
            return true
        }
    }
    return false
}

/**
 * Attend the most engaged user according to this hierarchy:
 *     1. Person looking at Furhat
 *     2. Last one to speak to Furhat
 *     3. Randomly alternate between user standing closest and random user
 */
fun Furhat.attendMostEngagedUser_DEPRACATED() {
    println("attending most engaged user")
    when {
        isAttended() -> {
            println("furhat is attended find next user to attend")
            attendAttendingUser_DEPRACATAED()
        }
        userThatLastSpoke != "" -> {
            println("there is a user that has spoken")
            attendIfPossible(users.getUser(userThatLastSpoke))
        }
        else -> {
            println("try to attend closest user")
            if (nextBoolean()) attendIfPossible(users.userClosestToFurhat) else attendIfPossible(users.random)
        }
    }
}

fun Furhat.attendAttendingUser_DEPRACATAED() {
    // There can be more than one candidate. We need to decide who to choose.
    var userSwitchCandidate: String? = null
    when (users.usersAttendingFurhat.count()) {
        1 -> userSwitchCandidate = users.usersAttendingFurhat[0].id
        in 2..MAX_NUMBER_OF_USERS -> {
            for (user in users.usersAttendingFurhat) {
                // Select user that last spoke
                if (userThatLastSpoke != "" && user.id == userThatLastSpoke) {
                    userSwitchCandidate = user.id
                    break
                }
            }
        }
        else -> userSwitchCandidate = userSwitchCandidate ?: users.userClosestToFurhat.id
    }
    attendIfPossible(users.getUser(userSwitchCandidate))
}

fun Furhat.attendOtherOrNobody() {
    when {
        users.count < 2 -> attendNobody(System.currentTimeMillis())
        users.count >= 2 -> attend(users.other)
    }
}

fun Furhat.shouldSwitchUserAttention(user: User): Boolean {
    println("should I switch attention? ")
    when {
        // don't switch if we are already attending them
        users.current == user -> {
            println("No, already attending"); return false
        }
        // don't switch if the last switch was too recent
        lastUserSwitchWasTooRecent() -> {
            println("no, last switch too recent"); return false
        }
        // Switch if we have been staring too long
        hasStaredTooLongAtUser(user) -> {
            println("yes, been staring for too long");return true
        }
        else -> {
            println("no, no reason for switching");return true
        }
    }
}

fun Furhat.attendIfPossible(user: User) {
    if (shouldSwitchUserAttention(user)) {
        attend(user)
        // TODO break out the smile
        gesture(Gestures.BigSmile(0.8))
        val currentTime = System.currentTimeMillis()
        // set time when furhat started attending.
        lastUserSwitchTimeStamp = currentTime
        user.furhatStartedAttendingUser = currentTime
        println("switched attention to ${user.id}")
    } else {
        println("not switching attention")
    }
}

fun Furhat.attendIfPossible(userID: String) {
    attendIfPossible(users.getUser(userID))
}

fun Furhat.hasStaredTooLongAtUser(user: User): Boolean {
    if (isAttendingUser && user.furhatStartedAttendingUser > 0) {
        println("stared for too long = " + ((System.currentTimeMillis() - user.furhatStartedAttendingUser) > MAX_TIME_TO_ATTEND_USER))
        return (System.currentTimeMillis() - user.furhatStartedAttendingUser > MAX_TIME_TO_ATTEND_USER)
    } else return false
}

fun Furhat.attendNobody(timeStamp: Long) {
    lastAttendNobodyTimeStamp = timeStamp
    attendNobody()
}

fun isItTimeToAttendAUser(): Boolean {
    println("is it time to attend a user? " + ((System.currentTimeMillis() - lastAttendNobodyTimeStamp) > WAIT_TIME_TO_ATTEND))
    return ((System.currentTimeMillis() - lastAttendNobodyTimeStamp) > WAIT_TIME_TO_ATTEND)
}

fun lastUserSwitchWasTooRecent(): Boolean {
    /*  println("current time: "+ System.currentTimeMillis())
      println("last switch: " + lastUserSwitchTimeStamp)*/
    return if (lastUserSwitchTimeStamp == 0.toLong()) {
        false //we've haven't done any user switch yet
    } else {
        println("last user switch was too recent: " + ((System.currentTimeMillis() - lastUserSwitchTimeStamp) < MIN_TIME_TO_ATTEND_USER))
        ((System.currentTimeMillis() - lastUserSwitchTimeStamp) < MIN_TIME_TO_ATTEND_USER)
    }
}