package furhatos.app.myadvancedskill.setting

import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.Voice

class Persona(val name: String, val mask: String = "adult", val face: List<String>, val voice: List<Voice>)

fun FlowControlRunner.activate(persona: Persona) {
    for (voice in persona.voice) {
        if (voice.isAvailable) {
            furhat.voice = voice
            break
        }
    }

    for (face in persona.face) {
        if (furhat.faces.get(persona.mask)?.contains(face)!!) {
            furhat.character = face
            break
        }
    }
}

val furhatPersona = Persona(
        name = "Hendrik",
        face = listOf(
                "Jamie",
                "default"), // Backup if Jamie is not available
        voice = listOf(
                PollyNeuralVoice.Brian()
                //PollyNeuralVoice.Matthew()
        )//.shuffled() randomize what voice to select
)