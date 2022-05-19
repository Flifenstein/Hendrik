package furhatos.app.myadvancedskill.flow.main

import furhatos.app.myadvancedskill.flow.Idling
import furhatos.app.myadvancedskill.flow.Passive
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.setting.ATTEND_TIME_TO_START
import furhatos.app.myadvancedskill.setting.MAX_DISTANCE_TO_GREET
import furhatos.app.myadvancedskill.utils.*
import furhatos.flow.kotlin.*
import furhatos.records.Location
import furhatos.records.User

/** State where Furhat is inactive with no users in front of it
 * Parent state: Idling will do random idle behavior and */
val WaitingForUser: State = state(Idling) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        if (users.hasAny()) {
            goto(WaitingForUserAttention)
        }
        furhat.attendNobody(System.currentTimeMillis())
    }
}

/** State where Furhat is passive with users in front of it
 *  Trying to determine whether they want to interact based on where they are looking
 *  * Todo: listen for a greeting in a parallel state.
 * */
// TODO change parent to passive
val WaitingForUserAttention: State = state(Passive) {
    onEntry {
        // initiate parallel flow to listen for changes in user attention
        // parallel(abortOnExit = true) { goto(AttentionListener) }
        println("entering ${thisState.name} " + mode)
        when (users.count) {
            0 -> goto(WaitingForUser)
            1 -> furhat.attend(users.list[0])
            else -> furhat.attend(users.nextMostEngagedUser())
        }

        // If we already have a user attending, resend the event to trigger the handler in this state
        if (users.usersAttendingFurhat.isNotEmpty()){
            raise(UserStartAttendEvent(users.usersAttendingFurhat[0]))
        }
    }
    onUserAttend {
        raise(UserStartAttendEvent(it))
    }
    onEvent<UserStartAttendEvent>{
        if (it.user.head.location.distance(Location.ORIGIN) < MAX_DISTANCE_TO_GREET) { // user is close enough
            println("user started attending furhat")
            parallel(WaitUserAttend(it.user), abortOnExit = true) // parallel flow to wait and make sure user is really engaged.
        }
    }
    onEvent<UserAttentionGainedEvent> {
        println("user attended in ${thisState.name}")
        furhat.attend(it.user)
        goto(EngageUser)
    }
}

fun WaitUserAttend(user: User) = state {
    onTime(delay = ATTEND_TIME_TO_START) {
        if (user.isAttendingFurhat) { // user is still attending Furhat
            // user is close enough
            send(UserAttentionGainedEvent(user))
        }
        terminate()
    }
}
