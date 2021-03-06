package com.example.testnavigationviewjsonitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.testnavigationviewjsonitem.ui.ImageFragment
import com.example.testnavigationviewjsonitem.ui.TextFragment
import com.example.testnavigationviewjsonitem.ui.WebFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.*
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
        when (savedInstanceState) {
            null ->
                listViewContainer.performItemClick(listViewContainer,0,0)
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

    private val name = ArrayList<String>()
    private val func = ArrayList<String>()
    private val param = ArrayList<String>()
    private val bundle = Bundle()
    private val imageFragment = ImageFragment()
    private val textFragment = TextFragment()
    private val webFragment = WebFragment()
    private val URL = "https://www.dropbox.com/s/fk3d5kg6cptkpr6/menu.json?dl=1"
    private var okHttpClient: OkHttpClient = OkHttpClient()


    private fun addMenuItemInNavMenuDrawer() {


        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                var menu = JSONObject(json)
                val jsonArray = menu.getJSONArray("menu")

                for (i in 0 until jsonArray.length()) {
                    var jsonObj = jsonArray.getJSONObject(i)

                    name.add(jsonObj.getString("name"))
                    func.add(jsonObj.getString("function"))
                    param.add(jsonObj.getString("param"))


                    runOnUiThread {
                        var ad = ArrayAdapter<String>(
                            this@MainActivity,
                            android.R.layout.simple_list_item_1,
                            name
                        )
                        listViewContainer.adapter = ad


                        listViewContainer.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, position, _ ->

                                when {
                                    func[position] == "text" -> {
                                        supportFragmentManager.beginTransaction()
                                            .detach(textFragment)
                                            .attach(textFragment)
                                            .replace(R.id.fragmentLayoutContainer, textFragment)
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
                    }

                }


            }


        })
    }

}