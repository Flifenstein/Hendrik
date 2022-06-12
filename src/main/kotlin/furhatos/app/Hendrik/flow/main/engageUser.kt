package furhatos.app.myadvancedskill.flow.main

import furhatos.app.Hendrik.flow.formal.Presentation

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode

import furhatos.app.myadvancedskill.setting.*
import furhatos.app.myadvancedskill.utils.gestures.hearSpeechGesture

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

/** State where Furhat enagage actively with the user*/
val EngageUser: State = state(Active) {
    include(AutoGlanceAway) // Glance away after some time of eye contact

    onEntry {
        println("entering ${thisState.name} " + mode)
        /** Set listening behavior **/
        furhat.setMicroexpression(LISTENING_MICROEXPRESSIONS) // TODO Can we move it to the parent state? Or move to a parallel state?

        /** Decide on who should have the initiative **/
        furhat.gesture(Gestures.BigSmile(0.8))
        goto(Presentation)

    }
    onReentry {
        furhat.say("moving on to next state")
    }

    onNoResponse {
        reentry()
    }
    onResponse {
        furhat.attend(it.userId)
        furhat.gesture(hearSpeechGesture)
        reentry()
    }
    onExit {
        furhat.setMicroexpression(DEFAULT_MICROEXPRESSIONS)
    }
}

