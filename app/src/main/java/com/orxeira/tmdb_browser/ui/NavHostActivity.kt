package com.orxeira.tmdb_browser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import com.orxeira.tmdb_browser.R

class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            findNavController(R.id.nv_host_fragment).popBackStack()
            return false
        }
        return super.onOptionsItemSelected(item)
    }
}