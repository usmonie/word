package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordRepository

interface GetAllFavouritesUseCase : CoroutineUseCase<Unit, List<WordCombined>>

internal class GetAllFavouritesUseCaseImpl(
    private val wordRepository: WordRepository
) : GetAllFavouritesUseCase {
    override suspend fun invoke(input: Unit): List<WordCombined> {
        return wordRepository.getAllFavorites()
    }
}
