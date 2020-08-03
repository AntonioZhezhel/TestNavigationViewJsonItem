package com.example.testnavigationviewjsonitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.testnavigationviewjsonitem.ui.ImageFragment
import com.example.testnavigationviewjsonitem.ui.TextFragment
import com.example.testnavigationviewjsonitem.ui.WebFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_text.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        addMenuItemInNavMenuDrawer()
        when {
               savedInstanceState == null ->
            lV.performItemClick(lV,0,0)
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true    }

    private val arr = ArrayList<String>()
    private val func = ArrayList<String>()
    private val param = ArrayList<String>()
    private val bundle = Bundle()
    private val imageFragment = ImageFragment()
    private val textFragment = TextFragment()
    private val webFragment = WebFragment()


    private fun addMenuItemInNavMenuDrawer() {



        var json: String? = null

        try {

            val inputStream: InputStream = assets.open("MainJson.json")
            json = inputStream.bufferedReader().use { it.readText() }

            var menu = JSONObject(json)
            var jsonArray = menu.getJSONArray("menu")



            for (i in 0 until jsonArray.length()) {
                var jsonObj = jsonArray.getJSONObject(i)

                arr.add(jsonObj.getString("name"))
                func.add(jsonObj.getString("function"))
                param.add(jsonObj.getString("param"))




            }
            var ad = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr)
            lV.adapter = ad




            lV.onItemClickListener= AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, param[position], Toast.LENGTH_LONG).show()



                when {
                    func[position] == "text" -> {
                        supportFragmentManager.beginTransaction()
                            .detach(textFragment)
                            .attach(textFragment)
                            .replace( R.id.fragmentLayoutContainer,textFragment)
                            .commit()
                        bundle.putString("key", param[position])
                        textFragment.arguments = bundle

                    }
                    func[position] == "image" -> {
                        bundle.putString("key", param[position])
                        imageFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .detach(imageFragment)
                            .attach(imageFragment)
                            .replace(R.id.fragmentLayoutContainer, imageFragment)
                            .commit()
                    }
                    func[position] == "url" -> {
                        bundle.putString("key", param[position])
                        webFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .detach(webFragment)
                            .attach(webFragment)
                            .replace(R.id.fragmentLayoutContainer, webFragment)
                            .commit()
                    }
                }

                drawer_layout.closeDrawer(GravityCompat.START)
            }



        } catch (e: IOException) {

        }
        nav_view.invalidate()
    }

}