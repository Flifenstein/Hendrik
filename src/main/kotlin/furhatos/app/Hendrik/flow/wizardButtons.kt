package furhatos.app.myadvancedskill.flow

import furhatos.app.myadvancedskill.flow.main.*
import furhatos.app.myadvancedskill.setting.DEFAULT_MICROEXPRESSIONS
import furhatos.app.myadvancedskill.setting.LISTENING_MICROEXPRESSIONS
import furhatos.flow.kotlin.*
import furhatos.nlu.Intent


val UniversalWizardButtons = partialState {
    onButton("restart", color = Color.Red, section = Section.RIGHT) {
        goto(Start)
    }
}

val TestButtons = partialState {
    onButton("sleep", color = Color.Blue, section = Section.RIGHT) {
        goto(DEFAULT_SLEEP_STATE)
    }
    onButton("start", color = Color.Blue, section = Section.RIGHT) {
        goto(DEFAULT_START_STATE)
    }
    onButton("Idle", color = Color.Blue, section = Section.RIGHT) {
        goto(DEFAULT_IDLE_STATE)
    }
    onButton("WakeUp", color = Color.Blue, section = Section.RIGHT) {
        goto(DEFAULT_WAKEUP_STATE)
    }
    onButton("Greeting", color = Color.Blue, section = Section.RIGHT) {
        goto(Greeting(null))
    }

    onButton("TestDefaultMicroExp", color = Color.Blue, section = Section.RIGHT) {
        furhat.setMicroexpression(DEFAULT_MICROEXPRESSIONS)
    }
    onButton("TestListeningMicroExp", color = Color.Blue, section = Section.RIGHT) {
        furhat.setMicroexpression(LISTENING_MICROEXPRESSIONS)
    }
    onButton("stopListening", color = Color.Red, section = Section.RIGHT) {
        furhat.stopListening()
    }
}