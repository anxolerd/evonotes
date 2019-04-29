package io.github.anxolerd.evonotes

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.anxolerd.evonotes.di.Injector
import io.github.anxolerd.evonotes.di.InjectorImpl

public class EvoNotesApp : Application() {
    public val di: Injector by lazy { InjectorImpl() }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}