package io.github.anxolerd.evonotes

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.anxolerd.evonotes.di.Injector
import io.github.anxolerd.evonotes.di.InjectorImpl

class EvoNotesApp : Application() {
    val di: Injector by lazy { InjectorImpl(this.applicationContext) }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}