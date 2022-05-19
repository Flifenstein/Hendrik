package furhatos.app.myadvancedskill.flow

import furhat.libraries.standard.GesturesLib
import furhatos.app.myadvancedskill.flow.main.EngageUser
import furhatos.app.myadvancedskill.flow.main.Nap
import furhatos.app.myadvancedskill.flow.main.WaitingForUser
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.gestures.Gesture

enum class Modes() {
    IDLE,
    PASSIVE,
    ACTIVE,
    SLEEPING
}

/** Mode indicates the state of the robot.
 * It can be set in a dialogstate to trigger behaviour specific to the state of the robot.
 * E.g. LED lights, micro expressions, automatic head movements, etc
 **/
var mode = Modes.IDLE

val DEFAULT_START_STATE = EngageUser
val DEFAULT_WAKEUP_STATE = WaitingForUser
val DEFAULT_IDLE_STATE = WaitingForUser
val DEFAULT_SLEEP_STATE = Nap

/** Extension function to the 'goto' state transition function to include a gesture.  */
fun FlowControlRunner.goto(gotoState: State, gesture: Gesture) {
    furhat.gesture(gesture)
    goto(gotoState)
}


/** Extended goto function to send the robot to sleep. **/
fun FlowControlRunner.gotoSleep(gotoState: State) {
    furhat.gesture(GesturesLib.PerformFallAsleepPersist)
    mode = Modes.SLEEPING
    goto(gotoState)
}

fun FlowControlRunner.gotoSleep() {
    gotoSleep(DEFAULT_WAKEUP_STATE)
}

fun FlowControlRunner.gotoWakeUp() {
    gotoWakeUp(DEFAULT_WAKEUP_STATE)
}

fun FlowControlRunner.gotoWakeUp(gotoState: State) {
    furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake)
    when (gotoState.parent?.name) {
        "Active" -> mode = Modes.ACTIVE
        "Passive" -> mode = Modes.PASSIVE
        "Idling" -> mode = Modes.IDLE
    }
    goto(gotoState)
}

/**
fun FlowControlRunner.gotoIdle() {
gotoIdle(DEFAULT_IDLE_STATE)
}

fun FlowControlRunner.gotoIdle(gotoState: State) {
mode = Modes.IDLE
goto(gotoState)
}

fun FlowControlRunner.gotoActive() {
gotoActive(DEFAULT_START_STATE)
}

fun FlowControlRunner.gotoActive(gotoState: State) {
mode = Modes.ACTIVE
goto(gotoState)
}

fun FlowControlRunner.gotoPassive(gotoState: State) {
mode = Modes.PASSIVE
goto(gotoState)
}
 **/