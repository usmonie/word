package com.usmonie.word.features.quotes.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.usecases.QuotesSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val QUOTES_DB_PATH = "quotes.db"

internal actual val roomModule: Module = module {
    single(named(QuotesDatabase::class.toString())) {
        val appContext = androidContext().applicationContext
        val assets = appContext.assets.open(QUOTES_DB_PATH)
        assets.available()
        val dbFile = appContext.getDatabasePath(QUOTES_DB_PATH)
        Room.databaseBuilder<QuotesDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
            .createFromInputStream(
                { assets },
                callback = object : RoomDatabase.PrepackagedDatabaseCallback() {
                    override fun onOpenPrepackagedDatabase(db: SupportSQLiteDatabase) {
                        println("PREPACKAGED: openDatabase")
                        super.onOpenPrepackagedDatabase(db)
                    }
                }
            )
    }

    factory<QuotesSourceFactory> {
        QuotesSourceFactory(androidContext().applicationContext)
    }
}
