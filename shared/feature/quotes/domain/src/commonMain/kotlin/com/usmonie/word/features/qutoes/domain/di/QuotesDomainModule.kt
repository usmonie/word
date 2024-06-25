package com.usmonie.word.features.qutoes.domain.di

import com.usmonie.word.features.qutoes.domain.usecases.GetFavoriteQuotesUseCase
import com.usmonie.word.features.qutoes.domain.usecases.GetFavoriteQuotesUseCaseImpl
import com.usmonie.word.features.qutoes.domain.usecases.GetNextPhraseUseCase
import com.usmonie.word.features.qutoes.domain.usecases.GetNextPhraseUseCaseImpl
import com.usmonie.word.features.qutoes.domain.usecases.GetRandomQuoteUseCase
import com.usmonie.word.features.qutoes.domain.usecases.GetRandomQuoteUseCaseImpl
import com.usmonie.word.features.qutoes.domain.usecases.UpdateFavoriteQuoteUseCase
import com.usmonie.word.features.qutoes.domain.usecases.UpdateFavoriteQuoteUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val quotesDomainModule = module {
    singleOf(::GetRandomQuoteUseCaseImpl) {
        bind<GetRandomQuoteUseCase>()
    }

    singleOf(::GetNextPhraseUseCaseImpl) {
        bind<GetNextPhraseUseCase>()
    }

    singleOf(::GetFavoriteQuotesUseCaseImpl) {
        bind<GetFavoriteQuotesUseCase>()
    }

    singleOf(::UpdateFavoriteQuoteUseCaseImpl) {
        bind<UpdateFavoriteQuoteUseCase>()
    }
}