package com.example.sample

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.comicsforall.AccessibilityCanvasEvent
import com.example.comicsforall.AccessibilityCanvasItemListener
import com.example.comicsforall.model.AccessibilityCanvasItem
import com.example.comicsforall.model.AccessibilityItem
import com.example.comicsforall.model.RectFrame
import com.example.comicsforall.util.setAccessibilityList
import com.example.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TextDialogListener, AccessibilityCanvasItemListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.imgManga.setAccessibilityList(accessibilityList)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun openCloseDrawingCanvas() {
        with(binding.canvas) {
            if (isDrawing) {
                finishDrawing()
            } else {
                openDrawCanvas(this@MainActivity)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_next -> return false
            R.id.action_draw -> openCloseDrawingCanvas()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSendEvent(event: AccessibilityCanvasEvent) {
        binding.canvas.sendEvent(event)
    }

    override fun onAccessibilityItemDraw(item: AccessibilityCanvasItem) {
        ItemDialogFragment(item).show(supportFragmentManager, ItemDialogFragment.TAG)
    }

    companion object {
        private val accessibilityList = listOf(
            AccessibilityItem(
                text = "teste",
                frame = RectFrame(100,200, 300, 400)
            ))
    }
}