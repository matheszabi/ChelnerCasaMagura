package ro.matheszabi.casamagura

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var loadedMenu: TreeNode<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadRestaurantMenu()
    }

    private fun loadRestaurantMenu() {
        val isRaw = resources.openRawResource(R.raw.menu_magura)
        val bout = ByteArrayOutputStream()
        val readBuffer = ByteArray(4 * 1024)
        var content: String

        isRaw.use {
            var read: Int
            do {
                read = it.read(readBuffer, 0, readBuffer.size)
                if (read == -1) {
                    break
                }
                bout.write(readBuffer, 0, read)
            } while (true)

            content = String(bout.toByteArray())
            loadedMenu = MenuLoader().loadMenu(content)
        }


        val row1ButtonClickListener = Row1ButtonClickListener();
        val children1 = loadedMenu.children;
        if (children1 != null) {
            val toolbar1 = findViewById<LinearLayout>(R.id.toolbar1)
            // select the first:
            var firstButton: Button? = null
            for ((i, child1) in children1.withIndex()) {
                val button = Button(this)
                button.text = child1.data
                button.transformationMethod = null
                var params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                );
                //params.gravity = Gravity.RIGHT;
                button.layoutParams = params
                button.tag = child1.data
                button.setOnClickListener(row1ButtonClickListener)
                toolbar1.addView(button)
                if (i == 0) {
                    firstButton = button
                }
            }
            firstButton?.performClick()
        }

    }

    private inner class Row1ButtonClickListener : View.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            val toolbar2 = findViewById<FlowLayout>(R.id.toolbar2)
            toolbar2.removeAllViews()
            toolbar2.invalidate()
            //
            val row2ButtonClickListener = Row2ButtonClickListener();

            var firstButton: Button? = null

            for (menu1 in loadedMenu.children) {
                if (menu1.data == v?.tag) {
                    for ((i, menu2) in menu1.children.withIndex()) {

                        val button = Button(this@MainActivity)
                        button.text = menu2.data
                        button.transformationMethod = null
                        var params = FlowLayout.LayoutParams(1, 1);
                        button.layoutParams = params
                        button.tag = menu2.data
                        button.setOnClickListener(row2ButtonClickListener)
                        toolbar2.addView(button)
                        if (i == 0) {
                            firstButton = button
                        }
                    }
                    break
                }
            }
            firstButton?.performClick()
        }
    }


    private inner class Row2ButtonClickListener : View.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            val toolbar3 = findViewById<FlowLayout>(R.id.toolbar3)
            toolbar3.removeAllViews()
            toolbar3.invalidate()

            val row3ButtonClickListener = Row3ButtonClickListener()

            for (menu1 in loadedMenu.children) {// toolbar1
                for (menu2 in menu1.children) {
                    if (menu2.data == v?.tag) {
                        for (menu3 in menu2.children) {
                            val button = Button(this@MainActivity)
                            button.text = menu3.data
                            button.transformationMethod = null
                            var params = FlowLayout.LayoutParams(1, 1);
                            button.layoutParams = params
                            button.tag = menu3.data
                            button.setOnClickListener(row3ButtonClickListener)
                            toolbar3.addView(button)
                        }

                        break
                    }
                }
            }
        }
    }


    private inner class Row3ButtonClickListener : View.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            Toast.makeText(this@MainActivity, "Clicked: " + v?.tag, Toast.LENGTH_SHORT).show()
        }
    }
}
