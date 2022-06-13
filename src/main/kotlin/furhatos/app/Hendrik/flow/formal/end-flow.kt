package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

/**
 * Subflow for ending the conversation with the formal CA.
 **/

/** Ask if the answers can be registered in the system **/
val AnswerRegistration: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        val registrationConsent: Boolean? = furhat.askYN("Your answers have been registered. Do you want to send them?") {
            onNoResponse {
                furhat.say("I'll wait until you are ready")
            }
        }

        if (registrationConsent == true) {
            furhat.say (
                "Thank you."
                // TODO register all the answers
            )
        } else {
            furhat.say (
                "Ok, I understand. I'm deleting your answers, don't worry."
                // TODO discard all the answers
            )
        }

        furhat.gesture(Gestures.BigSmile)
        goto(EndState)
    }
}

/** The conversation with the Furhat comes to an end **/
val EndState: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "The questionnaire has finished."
        )
        terminate()
    }
}
