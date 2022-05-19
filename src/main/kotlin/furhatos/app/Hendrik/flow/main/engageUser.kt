package furhatos.app.myadvancedskill.flow.main

import furhatos.app.meetfurhat.flow.modules.how_are_you.HowAreYou
import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.nlu.HowAreYouIntent
import furhatos.app.myadvancedskill.nlu.NiceToMeetYouIntent
import furhatos.app.myadvancedskill.setting.*
import furhatos.app.myadvancedskill.utils.gestures.hearSpeechGesture
import furhatos.app.myadvancedskill.utils.usersAttendingFurhat
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.Intent
import furhatos.nlu.common.Greeting

/** State where Furhat enagage actively with the user*/
val EngageUser: State = state(Active) {
    include(AutoUserSwitching) // Switch user after a while
    include(AutoGlanceAway) // Glance away after some time of eye contact

    onEntry {
        println("entering ${thisState.name} " + mode)
        /** Set listening behavior **/
        furhat.setMicroexpression(LISTENING_MICROEXPRESSIONS) // TODO Can we move it to the parent state? Or move to a parallel state?

        /** Decide on who should have the initiative **/
        furhat.gesture(Gestures.BigSmile(0.8))
        furhat.say("I'm listening. ") // TODO remove later
        furhat.listen() // We're leaving the initiative to the user
    }
    onReentry {
        when {
            !users.hasAny() -> goto(WaitingForUser) // no users
            users.hasAny() && users.usersAttendingFurhat.isEmpty() -> goto(WaitingForUserAttention) // have users, but no one is attending furhat
            else -> furhat.listen()
        }
    }
    onResponse<Greeting> {
        furhat.attend(it.userId)
        goto(Greeting(it.intent))
    }
    onResponse<HowAreYouIntent> {
        furhat.attend(it.userId)
        goto(Greeting(it.intent))
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

fun Greeting(intent: Intent? = null): State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        when {
            intent != null -> raise(intent)
            else -> raise(Greeting())
        }
        furhat.listen()
    }
    onResponse<Greeting> {
        furhat.say {
            random {
                +"Hi!"
                +"Hello!"
                +"Hi there!"
                +"Greetings!"
            }
        }
        //If encountered the user before, we will recognize them as "friend"
        if (!users.current.relation.isNullOrEmpty())
            furhat.say { +"${users.current.relation}" }
    }
    onResponse<HowAreYouIntent> {
        furhat.say {
            random {
                +"I feel great"
                +"I feel pretty good"
                +"I'm good, thank you"
            }
            +Gestures.BigSmile
        }
        call(HowAreYou)
        // TODO make sure furhat switches attention
        goto(EngageUser)
    }
    onResponse<NiceToMeetYouIntent> {
        furhat.say {
            random {
                +"Nice too meet you too. "
                +"My pleasure. "
                +"Nice to see you as well. "
            }
            +Gestures.BigSmile
        }
        call(HowAreYou)
        // TODO make sure furhat switches attention
        goto(EngageUser)
    }
    onNoResponse {
        goto(EngageUser)
    }
}