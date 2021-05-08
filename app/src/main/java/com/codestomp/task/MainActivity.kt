package com.codestomp.task

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.codestomp.task.databinding.ActivityMainBinding
import com.codestomp.task.ui.DrawerMenuAdapter
import com.codestomp.task.ui.DrawerMenuItem

class MainActivity : AppCompatActivity(), DrawerMenuAdapter.IItemClick {

    private lateinit var adapter: DrawerMenuAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val draweritemList= ArrayList<DrawerMenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initNavDrawerRV()

        if (savedInstanceState != null)
            updateRv(savedInstanceState)
    }

    private fun updateRv(savedInstanceState: Bundle?) {
        draweritemList.forEach{ item ->
            item.selected=false
        }
        val position = savedInstanceState!!.getInt("position")
        draweritemList[position].selected=true
    }

    private fun initNavDrawerRV()
    {

        draweritemList.add(DrawerMenuItem(R.drawable.explore,"Explore",true))
        draweritemList.add(DrawerMenuItem(R.drawable.live,"Live Chat"))
        draweritemList.add(DrawerMenuItem(R.drawable.gallery,"Gallery"))
        draweritemList.add(DrawerMenuItem(R.drawable.wishlist,"Wishlist"))
        draweritemList.add(DrawerMenuItem(R.drawable.emagazine,"E-Magazine"))
         adapter =DrawerMenuAdapter(draweritemList,this)
        binding.recyclerView.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onItemClicked(data: DrawerMenuItem) {
        draweritemList.forEach{ item ->
            item.selected=false
        }
        data.selected=true
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "You have clicked on ${data.description}", Toast.LENGTH_SHORT).show()
        binding.drawerLayout.closeDrawers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("position", adapter.getLastClickedPosition())
        super.onSaveInstanceState(outState)

    }
}