package furhatos.app.myadvancedskill.flow

import furhatos.app.Hendrik.flow.formal.Presentation
import furhatos.app.myadvancedskill.flow.main.WaitingForUser
import furhatos.app.myadvancedskill.flow.paralell.InteractionGlow
import furhatos.app.myadvancedskill.flow.paralell.ListenMonitor
import furhatos.app.myadvancedskill.setting.*
import furhatos.app.myadvancedskill.utils.testMode
import furhatos.autobehavior.bigSmileProbability
import furhatos.autobehavior.enableSmileBack
import furhatos.autobehavior.smileBlockDelay
import furhatos.autobehavior.smileProbability
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.LogisticMultiIntentClassifier

// TODO Nils: Break out these values to a settings file

// Nils: TODO verbose should be false as default


val Init: State = state {
    init {
        /** Set testMode for speedy testing */
        testMode = true

        /** Set our main character - defined in personas */
        activate(furhatPersona)

        /** define our default engagement parameters */
        furhat.param.endSilTimeout = DEFAULT_END_SIL_TIMEOUT
        furhat.param.noSpeechTimeout = DEFAULT_NO_SPEECH_TIMEOUT
        users.attentionGainedThreshold = DEFAULT_ATTENTION_GAINED_THRESHOLD
        users.attentionLostThreshold = DEFAULT_ATTENTION_LOST_THRESHOLD

        /** define our default smile back behavior */
        furhat.enableSmileBack = DEFAULT_ENABLE_SMILE_BACK
        smileProbability = DEFAULT_SMILE_PROBABILITY
        bigSmileProbability = DEFAULT_BIG_SMILE_PROBABILITY
        smileBlockDelay = DEFAULT_SMILE_BLOCK_DELAY


        /** Set the interaction space that governs when the robot will engage with users, and the number of users */
        users.setSimpleEngagementPolicy(INTERACTION_SPACE_INNER, INTERACTION_SPACE_OUTER, MAX_NUMBER_OF_USERS)

        /** enable alternate intent classifier
        see: https://docs.furhat.io/nlu/#alternative-classification */
        LogisticMultiIntentClassifier.setAsDefault()

        /** Set default microepressions **/
        furhat.setMicroexpression(DEFAULT_MICROEXPRESSIONS)

        /** Start parallel flow to manage the LED **/
        parallel(abortOnExit = false) { goto(InteractionGlow) }

        /** Start parallel flow to monitor the robot listening **/
        parallel(abortOnExit = false) { goto(ListenMonitor) }

        /** start the interaction */
        goto(Start)
    }
}

val Start: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        goto(Presentation)
        // goto(WaitingForUser)
    }
    onExit {
        if (furhat.isVirtual() && !testMode!! && !users.hasAny()) {
            furhat.say("Add a virtual User to start the interaction. ")
        }
    }
}