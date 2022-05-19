package furhatos.app.myadvancedskill.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

class SleepIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "sleep",
                "go to sleep"
        )
    }
}