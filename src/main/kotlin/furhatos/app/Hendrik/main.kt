package furhatos.app.myadvancedskill

import furhatos.app.myadvancedskill.flow.Init
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class MyAdvancedSkill : Skill() {
    override fun start() {
        /** Uploading the dialogues online: Pietro **/
        dialogLogger.startSession(cloudToken = "5147314a-1145-4ae2-aaa3-77d0037ce141")

        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
