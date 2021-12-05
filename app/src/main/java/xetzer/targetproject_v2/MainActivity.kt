package xetzer.targetproject_v2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationMenu: BottomNavigationView
    var target = MyDataAppClass.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restoreTargets()
        bottomNavigationMenu = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationMenu.setOnItemSelectedListener { item->
            var fragment:Fragment?=null
            when (item.itemId){
                R.id.main_target_fragment -> {fragment = MainTargetFragment()}
                R.id.add_target_fragment -> {fragment = AddTargetFragment()}
                R.id.list_target_fragment -> {fragment = ListTargetFragment()}
            }
            if (fragment == null)
                fragment = MainTargetFragment()
            replaceFragment(fragment)
            true
        }
        bottomNavigationMenu.selectedItemId = R.id.main_target_fragment
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun getDayTime(): String {
        val dayTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            date.format(formatter)
        } else {
            val date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            formatter.format(date)
        }
        return dayTime
    }

    private fun restoreTargets() {
        // TODO добавить функцию восставновления целей из памяти устройства и добавления их в коллекцию
        val dateTime = getDayTime()
        target.targetList.add(TargetClass("Измени образ мышления и ты именишь свою жизнь.", dateTime))
        target.targetList.add(TargetClass("Что имеешь приносит выгоду, что не имеешь - пользу.", dateTime))
        target.targetList.add(TargetClass("Путь воина - это гармония между действиями и решениями.", dateTime))
    }

}