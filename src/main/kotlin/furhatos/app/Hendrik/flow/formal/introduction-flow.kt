package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the formal introduction.
 **/

/** The Furhat presents itself and the program **/
val Presentation: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "Dear participant," +
                    "You are at the start of having a conversation with me, a virtual conversational agent. My name is Hendrik, nice to meet you."
        )

        goto(ConsentForm)
    }
}

/** Inform about the consent form **/
val ConsentForm: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "You have the right to withdraw before, during or immediately after this conversation."
        )

        goto(Privacy)
    }
}

/** Explain the privacy policy **/
val Privacy: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "This conversation will be audio recorded for reliability purposes. The audio recording of this conversation will be deleted within a maximum of 2 weeks. At all times, your data is anonymized. After this conversation, you will receive questions to answer about this conversation."
        )

        goto(Confirmation)
    }
}

/** Ask for confirmation **/
val Confirmation: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        val consent: Boolean? = furhat.askYN("Yes or no; do you wish to proceed this conversation?")

        if(consent == true){
            furhat.say("Ok. Let's start!")
            goto(OpeningQuestion)
        } else {
           goto(EndState)
        }
    }
}
