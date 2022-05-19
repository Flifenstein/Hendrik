package furhatos.app.meetfurhat.flow.modules.how_are_you

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class PositiveReactionIntent(
        val positiveExpressionEntity: PositiveExpressionEntity? = null,
        val negativeExpressionEntity: NegativeExpressionEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "@positiveExpressionEntity",
                "not @negativeExpressionEntity",
                "not too @negativeExpressionEntity"
        )
    }

    override fun getNegativeExamples(lang: Language): List<String> {
        return listOf(
                "not @positiveExpressionEntity",
                "@negativeExpressionEntity"
        )
    }
}

class PositiveExpressionEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "great",
                "fine:fine,define",
                "happy",
                "nice",
                "fantastic",
                "awesome",
                "good:good,best",
                "amazing",
                "incredible",
                "excellent",
                "perfect",
                "fabulous",
                "hilarious",
                "epic",
                "love",
                "very well",
                "really well",
                "excited",
                "astonished",
                "cool",
                "terrific",
                "cheerful",
                "contented",
                "delighted",
                "ecstatic",
                "elated",
                "glad",
                "joyful",
                "joyous",
                "jubilant",
                "lively",
                "merry",
                "overjoyed",
                "peaceful",
                "pleasant",
                "pleased",
                "thrilled",
                "upbeat",
                "blessed",
                "blest",
                "blissful",
                "blithe",
                "can't complain",
                "captivated",
                "chipper",
                "chirpy",
                "content",
                "convivial",
                "exultant",
                "gay",
                "gleeful",
                "gratified",
                "intoxicated",
                "jolly",
                "laughing",
                "light",
                "looking good",
                "mirthful",
                "on cloud nine",
                "peppy",
                "perky",
                "playful",
                "sparkling",
                "sunny",
                "tickled",
                "up",
                "alright:alright, all right",
                "splendid",
                "good-looking"
        )
    }
}

class NegativeExpressionEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "terrible:terrible,parable",
                "bad:bad,dad,worst",
                "horrible",
                "awful",
                "dreadful",
                "atrocious",
                "appalling",
                "shit",
                "sucks",
                "crap",
                "annoying",
                "unacceptable",
                "unprofessional",
                "too shabby",
                "flawed:flawed,flaws"
        )
    }
}
