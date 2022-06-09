package furhatos.app.myadvancedskill.flow

import furhat.libraries.standard.AutomaticHeadMovements.RandomHeadMovements
import furhatos.app.Hendrik.commandHandlers
import furhatos.app.myadvancedskill.setting.*
import furhatos.app.myadvancedskill.utils.*
import furhatos.flow.kotlin.*
import furhatos.skills.emotions.UserGestures
import java.util.concurrent.TimeUnit

/** Parent state that house global definitions that should always be active in any state */
val Parent: State = state {
    // Add global wizard buttons
    include(UniversalWizardButtons)
    // Add wizard buttons for easy testing
    if (testMode!!)
        include(TestButtons)
}

/** Additional more specific parents that can be used for enabling different modes. */

/** Active - use for all states where the robot is being actively engaged in the interaction.  */
val Active: State = state(Parent) {
    mode = Modes.ACTIVE
    // Random head movements can affect how gestures that include neck movements are carried out.
    // Either choose to not include random head movements at all
    // Or you can turn them on/off using enableVariableHeadMovements = false before performing the gesture
    include(RandomHeadMovements()) // TODO make Listening head movements (tilt head, less movements)
    onUserLeave {
        println("user left in ${thisState.name}")
        when {
            !users.hasAny() -> goto(DEFAULT_IDLE_STATE) // no users left
            it == users.current -> furhat.attend(users.nextMostEngagedUser()) // user the robot was looking at left
            else -> furhat.glance(it.head.location) // other user left
        }
        reentry()
    }
    onUserEnter {
        when {
            !furhat.isAttendingUser -> furhat.attend(it) // attend user if we are not attending a user
            else -> furhat.glance(it) // otherwise just glance at new user
        }
        reentry()
    }
    // Override default error messages on internet failure
    onResponseFailed {
        furhat.say("Sorry, I am having trouble with the internet connection.")
        reentry()
    }
    onNetworkFailed {
        furhat.say("Sorry, I am experiencing trouble with the internet connection.")
        reentry()
    }
    onEvent<unexpectedIdlingEvent>{
        println("Unexpectedly not listening or speaking in an active state")
        println("Restarting a listen")
        furhat.listen()
    }
    onExit(inherit = true) {
        furhat.setMicroexpression(DEFAULT_MICROEXPRESSIONS)
        // Reset local counters
        localOnNoMatchCount = 0
        localOnNoResponseCount = 0
    }
}

/** Passive - use for states where the robot is awake, but does not engage in the interaction.  */
val Passive: State = state(Parent) {
    mode = Modes.PASSIVE
    // Random head movements can affect how gestures that include neck movements are carried out.
    // Either choose to not include random head movements at all
    // Or you can turn them on/off using enableVariableHeadMovements = false before performing the gesture
    include(RandomHeadMovements())

    onUserEnter {
        println("user entered in ${thisState.name}")
        furhat.attendIfPossible(it)
    }
    onUserGesture(UserGestures.Smile) {
        println("user smiled in ${thisState.name}")
        val smilingUser = users.getUser(it.userID)
        // A smile is only relevant if it's directed towards Furhat
        if (smilingUser.isAttendingFurhat()) {
            furhat.attendIfPossible(smilingUser)
        }
    }
    onUserLeave {
        println("user left in ${thisState.name}")
        when {
            !users.hasAny() -> goto(DEFAULT_IDLE_STATE) // no users
            it == users.current -> furhat.attend(users.nextMostEngagedUser()) // user the robot was attending left
            else -> furhat.glance(it.head.location) // other user left
        }
    }
    // From time to time - check if we should switch user.
    onTime(
            delay = MIN_TIME_TO_ATTEND_USER,
            repeat = MIN_TIME_TO_ATTEND_USER,
            instant = false)
    {
        println("On time: Re-evaluate what user to attend")
        when {
            // 1. No users: go back to idle
            users.count == 0 -> goto(DEFAULT_IDLE_STATE) //
            // 2. Furhat is not attending anyone
            !furhat.isAttendingUser -> {
                if (isItTimeToAttendAUser()) {
                    furhat.attend(users.nextMostEngagedUser())
                } else {
                    println("Keep idling for now")
                }
            }
            // 3. Furhat is attending a user
            furhat.isAttendingUser -> {
                if (furhat.hasStaredTooLongAtUser(users.current)) {
                    when (users.count) {
                        1 -> furhat.attendNobody(System.currentTimeMillis())
                        else -> furhat.attendIfPossible(users.other) // find another user to attend
                    }
                } else {
                    // All is Good. Just keep looking at them.
                }
            }
        }
    }

    onTime(WAIT_TIME_TO_NAP) {
        furhat.attendNobody(System.currentTimeMillis())
        gotoSleep()
    }
}


/** Idling - use for sates where the robot is awake, but waiting for a user to engage with it.  */
val Idling: State = state(Parent) {
    mode = Modes.IDLE
    // TODO replace with idle head movements - bigger movements, looking around the room but avoiding eye contact height of gaze, add more gaze
    include(RandomHeadMovements())
    //idleHeadMovements()

    onEntry(inherit = true) {
        furhat.attendNobody()
    }
    onUserEnter {
        goto(DEFAULT_START_STATE)
    }
    onTime(WAIT_TIME_TO_NAP) {
        gotoSleep()
    }
}

/** Sleeping - use for states where the robot is lightly asleep  */
val Sleeping: State = state(Parent) {
    mode = Modes.SLEEPING
    include(commandHandlers)
    // TODO add breathing head movements
}


/** Sleeping - use for states where the robot is power save mode and can only be awaken by wizard buttons */
val PowerSaving: State = state(Parent) {
    mode = Modes.SLEEPING
    onEntry(inherit = true) {
        delay(5, TimeUnit.SECONDS) //stay asleep for at least 5 seconds before being able to wake up again
        reentry()
    }
    onButton("Wake Up") {
        gotoWakeUp()
    }
}