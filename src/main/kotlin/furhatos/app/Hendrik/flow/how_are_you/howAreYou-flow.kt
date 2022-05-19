package furhatos.app.meetfurhat.flow.modules.how_are_you

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.utils.attendOtherOrNobody
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.EnumEntity

/**
 * Subflow to ask about users feelings.
 **/
val HowAreYou: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask {
            random {
                // use prosody tag for emphasis
                +"How are <prosody rate='85%'>you today?"
                +"How do <prosody rate='85%'>you,</prosody> today?"
                +"How are <prosody rate='85%'>you,</prosody> today?"
            }
        }
    }
    onReentry {
        furhat.ask("How are <prosody rate='85%'>you,</prosody> doing today?")
    }

    onResponse<PositiveReactionIntent> {
        var word = ""
        // Check for what word the person used in the intent
        val positiveWord: EnumEntity? = it.intent.positiveExpressionEntity
        if (positiveWord != null) {
            furhat.say {
                +"glad to hear you feel ${positiveWord}"
                +delay(200)
            }
        } else {
            furhat.say {
                +"Nice to hear you feel good. "
            }
        }
        furhat.attendOtherOrNobody() // Make sure to shift attention to signal end of conversation.
        terminate()
    }

    onResponse {
        furhat.say {
            +Gestures.Thoughtful(0.6, 2.0)
            +"Well, feelings are complex."
        }
        delay(400)
        furhat.attendOtherOrNobody() // Make sure to shift attention to signal end of conversation.
        terminate()
    }

    onNoResponse {
        furhat.attendOtherOrNobody() // Make sure to shift attention to signal end of conversation.
        terminate()
    }
}
