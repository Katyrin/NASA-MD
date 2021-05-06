package com.katyrin.nasa_md.ui.main

import android.app.Application
import androidx.room.Room
import com.katyrin.nasa_md.ui.main.model.room.NotesDao
import com.katyrin.nasa_md.ui.main.model.room.NotesDataBase

class App: Application() {
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