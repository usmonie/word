package com.usmonie.word.features.new.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState


@Stable
sealed class HangmanState(
    open val word: WordCombinedUi,
    open val guessedLetters: Set<Char>,
    open val incorrectGuesses: Int,
) : ScreenState {
    sealed class Playing(
        word: WordCombinedUi,
        guessedLetters: Set<Char> = setOf(),
        incorrectGuesses: Int = 0
    ) : HangmanState(word, guessedLetters, incorrectGuesses) {
        data class Input(
            override val word: WordCombinedUi,
            override val guessedLetters: Set<Char> = setOf(),
            override val incorrectGuesses: Int = 0
        ) : Playing(word, guessedLetters, incorrectGuesses)

        data class Information(
            override val word: WordCombinedUi,
            override val guessedLetters: Set<Char> = setOf(),
            override val incorrectGuesses: Int = 0
        ) : Playing(word, guessedLetters, incorrectGuesses)
    }

    data class Lost(
        override val word: WordCombinedUi,
        override val guessedLetters: Set<Char>,
    ) : HangmanState(word, guessedLetters, 6)

    data class Won(
        override val word: WordCombinedUi,
        override val guessedLetters: Set<Char>,
    ) : HangmanState(word, guessedLetters, 0)
}

sealed class HangmanAction : ScreenAction {
    data class GuessLetter(val letter: Char) : HangmanAction()
    data class OpenWord(val word: WordCombinedUi) : HangmanAction()
    data object UpdateWord : HangmanAction()
    data object ShowHint : HangmanAction()
}

sealed class HangmanEvent : ScreenEvent {
    data object UpdateHint : HangmanEvent()
    data class OpenWord(val word: WordCombinedUi) : HangmanEvent()
    data class UpdateWord(val word: WordCombinedUi) : HangmanEvent()
    data class RightLetterGuessed(val letter: Char) : HangmanEvent()
    data class WrongLetterGuessed(val letter: Char) : HangmanEvent()
    data class Lost(val letter: Char) : HangmanEvent()
    data class Won(val letter: Char) : HangmanEvent()
}

@Stable
sealed class HangmanEffect : ScreenEffect {
    class Won : HangmanEffect()
    class Lost : HangmanEffect()
    class RestartGame : HangmanEffect()
    data class OpenWord(val word: WordCombinedUi) : HangmanEffect()
}