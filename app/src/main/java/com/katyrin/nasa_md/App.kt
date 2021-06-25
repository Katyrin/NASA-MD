package com.katyrin.nasa_md

import androidx.room.Room
import com.katyrin.nasa_md.di.DaggerAppComponent
import com.katyrin.nasa_md.scheduler.DefaultSchedulers
import com.katyrin.nasa_md.ui.main.model.room.NotesDao
import com.katyrin.nasa_md.ui.main.model.room.NotesDataBase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.terrakok.cicerone.Cicerone

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerAppComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()
                withRouter(cicerone.router)
                withNavigatorHolder(cicerone.navigatorHolder)
            }
            .withSchedulers(DefaultSchedulers)
            .build()

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: App
        private var db: NotesDataBase? = null
        private const val DB_NAME = "MainDataBase.db"

        private fun getMainDB(): NotesDataBase? {
            if (db == null) {
                synchronized(NotesDataBase::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            appInstance.applicationContext,
                            NotesDataBase::class.java, DB_NAME
                        ).build()
                    }
                }
            }
            return db
        }

        fun getNotesDao(): NotesDao {
            return getMainDB()!!.notesDao()
        }
    }
}