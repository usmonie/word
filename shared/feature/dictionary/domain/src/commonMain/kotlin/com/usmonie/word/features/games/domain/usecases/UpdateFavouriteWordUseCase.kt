package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.games.domain.repository.WordsRepository

/**
 * Use case for adding a word to favorites.
 */
interface UpdateFavouriteWordUseCase : CoroutineUseCase<UpdateFavouriteWordUseCase.Param, Unit> {

    /**
     * Data class representing the parameters required to add a word to favorites.
     *
     * @property word The `WordDomain` object to be added to favorites.
     */
    data class Param(val word: String, val isFavorite: Boolean)
}

internal class UpdateFavouriteWordUseCaseImpl(
    private val wordsRepository: WordsRepository
) : UpdateFavouriteWordUseCase {

    /**
     * Invokes the use case to add a word to the favorites in the repository.
     *
     * @param input The `Param` object containing the word to be added to favorites.
     */
    override suspend fun invoke(input: UpdateFavouriteWordUseCase.Param) {
        if (input.isFavorite) {
            wordsRepository.deleteFavorite(input.word)
        } else {
            wordsRepository.addFavorite(input.word)
        }
    }
}
