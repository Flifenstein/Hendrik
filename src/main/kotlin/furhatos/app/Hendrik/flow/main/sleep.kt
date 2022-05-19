package furhatos.app.myadvancedskill.flow.main

import furhat.libraries.standard.NluLib
import furhatos.app.myadvancedskill.flow.PowerSaving
import furhatos.app.myadvancedskill.flow.Sleeping
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.setting.NAP_TIME_BEFORE_DEEP_SLEEP
import furhatos.app.myadvancedskill.setting.NapHeadMovements
import furhatos.app.myadvancedskill.utils.isAttended
import furhatos.flow.kotlin.*
import furhatos.records.Location
import randomHeadmovements.gesturePosReset

/**
 * Light sleep. Wakes up when someone comes close.
 */
val Nap: State = state(Sleeping) {
    include(NapHeadMovements)
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.attend(Location(0.0, -1.0, 3.0))
    }
    onTime(NAP_TIME_BEFORE_DEEP_SLEEP) {
        goto(DeepSleep)
    }
    onUserAttend {
        if (furhat.isAttended()) {
            furhat.listen()
        } else {
            furhat.stopListening()
        }
    }
    onResponse<NluLib.WakeUp> {
        goto(EngageUser)
    }
    onResponse {
        if (furhat.isAttended()) {
            furhat.listen()
        }
    }
    onNoResponse {
        if (furhat.isAttended()) {
            furhat.listen()
        }
    }
    onNetworkFailed { }
    onResponseFailed { }
}

/**
 * Deep sleep. Can only wake up from wizard buttons in the web interface.
 */

val DeepSleep: State = state(PowerSaving) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.attend(Location(0.0, -1.0, 3.0))
        furhat.gesture(gesturePosReset)
        delay(200)
        furhat.setVisibility(false, 3000)
    }
    onExit {
        furhat.setVisibility(true, 3000)
    }
}

