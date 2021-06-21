package com.example.catchat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Реализация этого интерфейса
    //означает, что активность
    //может прослушивать щелчки
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        //кнопка вызова панели
        val toggle = ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        //Активность регистрируется в NavigationView
        //в качестве слушателя.
        navigationView.setNavigationItemSelectedListener(this)
        val fragment: Fragment = InboxFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.content_frame, fragment)
        ft.commit()
    }

    //Этот метод вызывается тогда, когда пользователь щелкает
    //на одной из команд панели.
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var fragment: Fragment? = null
        var intent: Intent? = null
        when (id) {
            R.id.nav_drafts -> fragment = DraftsFragment()
            R.id.nav_sent -> fragment = SentItemsFragment()
            R.id.nav_trash -> fragment = TrashFragment()
            R.id.nav_help -> intent = Intent(this, HelpActivity::class.java)
            R.id.nav_feedback -> intent = Intent(this, FeedbackActivity::class.java)
            else -> fragment = InboxFragment()
        }
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            //Отображает соответствующий фрагмент
            //или активность (в зависимости от команды,
            //выбранной на панели)
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        } else {
            startActivity(intent)
        }
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        //Панель закрывается, когда пользователь выбирает одну из команд.
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    //Когда пользователь нажимает
    //кнопку Назад при открытой навигационной панели, панель закрывается.
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}