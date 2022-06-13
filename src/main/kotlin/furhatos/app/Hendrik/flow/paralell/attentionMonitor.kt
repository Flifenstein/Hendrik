package furhatos.app.myadvancedskill.flow.paralell

import furhatos.app.myadvancedskill.setting.MAX_DISTANCE_TO_GREET
import furhatos.app.myadvancedskill.setting.WAIT_TIME_TO_NAP
import furhatos.app.myadvancedskill.setting.ATTEND_TIME_TO_START
import furhatos.app.myadvancedskill.setting.startedAttendingFurhat
import furhatos.app.myadvancedskill.utils.UserAttentionGainedEvent
import furhatos.app.myadvancedskill.utils.isAttended
import furhatos.app.myadvancedskill.utils.usersAttendingFurhat
import furhatos.event.monitors.MonitorAttend
import furhatos.event.senses.SenseUserAttend
import furhatos.event.senses.SenseUserEnter
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.records.Location

val AttentionListener: State = state() {
    onEntry {
        println("starting attention monitoring")
    }
    onEvent<SenseUserAttend> {
        val thisUser = users.getUser(it.userId)
        println("user attend start")
        thisUser.startedAttendingFurhat = System.currentTimeMillis()
    }
    onEvent<SenseUserEnter> {
        val thisUser = users.getUser(it.userId)
        println("user entered")
        if (thisUser.isAttendingFurhat) {
            println("user attend start")
            thisUser.startedAttendingFurhat = System.currentTimeMillis()
        }
    }
    // continously check if the person has been attending the robot for long enough time to indicate they want to start an interaction
    onTime(
            delay = ATTEND_TIME_TO_START,
            repeat = (WAIT_TIME_TO_NAP / ATTEND_TIME_TO_START),
            instant = true) {
        if (furhat.isAttended())
            raise("attentionCheck")
    }
    // onEvent<MonitorAttend>{}
    onEvent("attentionCheck", instant = true) {
        // Check the attention of all users attending to furhat
        for (user in users.usersAttendingFurhat) {
            // and has been looking for long enough to trigger the robot to take action
            if ((System.currentTimeMillis() - user.startedAttendingFurhat) > ATTEND_TIME_TO_START)
            // and is at close enough distance
                if (user.head.location.distance(Location.ORIGIN) < MAX_DISTANCE_TO_GREET)
                // Trigger an event
                    send(UserAttentionGainedEvent(user))
            break
        }
    }
}