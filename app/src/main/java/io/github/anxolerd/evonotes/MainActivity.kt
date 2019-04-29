package io.github.anxolerd.evonotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.github.anxolerd.evonotes.navigation.NavigationManager


class MainActivity : AppCompatActivity() {
    private var navigationManager: NavigationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Initialize navigation
        this.navigationManager = (this.application as EvoNotesApp).di.getNavigationManager()

        this.navigationManager?.init(this.application as EvoNotesApp, this.supportFragmentManager)
        navigationManager?.showNotesList()
    }
}
