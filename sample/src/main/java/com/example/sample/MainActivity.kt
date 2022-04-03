package com.example.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.comicsforall.AccessibilityCanvasEvent
import com.example.comicsforall.AccessibilityCanvasItemListener
import com.example.comicsforall.model.AccessibilityCanvasItem
import com.example.comicsforall.util.setAccessibilityList
import com.example.sample.databinding.ActivityMainBinding
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// TODO ViewModel, Repository
@ExperimentalSerializationApi
class MainActivity : AppCompatActivity(), TextDialogListener, AccessibilityCanvasItemListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.imgManga.setAccessibilityList(Json.decodeFromString(manga1Json))

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    private fun openCloseDrawingCanvas() {
        with(binding.canvas) {
            if (isDrawing) {
               val list = finishDrawing()
               Log.d("json", Json.encodeToString(list))
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
        private const val manga1Json = "[{\"text\":\"At the school class: Time to introduce the new transfer student, says the teacher\",\"frame\":{\"left\":542,\"top\":190,\"right\":651,\"bottom\":342}},{\"text\":\"Hello Everyone! My name is Yumeko Jabami. Says the beautifull Young Lady\",\"frame\":{\"left\":275,\"top\":151,\"right\":532,\"bottom\":396}},{\"text\":\" I may be new here but i´d be very happy to make friends with all of you.\",\"frame\":{\"left\":262,\"top\":406,\"right\":631,\"bottom\":641}},{\"text\":\" Whoa. She´s pretty cute. Whispers a student. Right? Though Jabami is a pretty weird last name.\\n Says another. Hmm...\",\"frame\":{\"left\":449,\"top\":656,\"right\":651,\"bottom\":934}},{\"text\":\"I`d like for one of you to show jabami-san around the academy. Says the teacher\",\"frame\":{\"left\":202,\"top\":726,\"right\":430,\"bottom\":935}}]"
    }
}