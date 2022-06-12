package furhatos.app.myadvancedskill.flow


import furhatos.flow.kotlin.*


val UniversalWizardButtons = partialState {
    onButton("restart", color = Color.Red, section = Section.RIGHT) {
        goto(Start)
    }
}

