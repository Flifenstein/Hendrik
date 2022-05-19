package furhatos.app.Hendrik

import furhat.libraries.standard.NluLib
import furhatos.app.myadvancedskill.flow.DEFAULT_SLEEP_STATE
import furhatos.app.myadvancedskill.flow.DEFAULT_WAKEUP_STATE
import furhatos.app.myadvancedskill.nlu.SleepIntent
import furhatos.flow.kotlin.*


val commandHandlers = partialState {
    onResponse<SleepIntent> {
        var sure = furhat.askYN("Are you sure?")
        when (sure) {
            true -> goto(DEFAULT_SLEEP_STATE)
            else -> {
                furhat.say("Ok")
                reentry()
            }
        }
    }
    onResponse<NluLib.WakeUp> {
        var sure = furhat.askYN("Are you sure?")
        when (sure) {
            true -> goto(DEFAULT_WAKEUP_STATE)
            else -> {
                furhat.say("Ok")
                reentry()
            }
        }
    }
}