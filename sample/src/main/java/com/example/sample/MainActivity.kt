package com.example.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.comicsforall.AccessibilityEvent
import com.example.comicsforall.AccessibilityItemListener
import com.example.comicsforall.model.AccessibilityItem
import com.example.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TextDialogListener, AccessibilityItemListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun enableDraw() {
        binding.canvas.openDrawCanvas(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_next -> return false
            R.id.action_draw -> enableDraw()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSendEvent(event: AccessibilityEvent) {
        binding.canvas.sendEvent(event)
    }

    override fun onAccessibilityItemDraw(item: AccessibilityItem) {
        ItemDialogFragment(item).show(supportFragmentManager, ItemDialogFragment.TAG)
    }
}