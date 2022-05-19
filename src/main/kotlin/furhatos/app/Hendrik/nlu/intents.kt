package furhatos.app.myadvancedskill.nlu

import furhatos.nlu.Intent
import furhatos.util.Language


class NiceToMeetYouIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "nice to meet you",
                "glad to meet you",
                "a pleasure to meet you",
                "nice to see you",
                "great to meet you",
                "happy to see you",
                "very nice to finally meet you",
                "fun to meet up with you"
        )
    }
}

class HowAreYouIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "how are you",
                "how are you doing today",
                "what's up",
                "how are things with you"
        )
    }
}