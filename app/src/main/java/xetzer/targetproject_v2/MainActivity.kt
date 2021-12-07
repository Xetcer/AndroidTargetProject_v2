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
        bottomNavigationMenu.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.main_target_fragment -> {
                    fragment = MainTargetFragment()
                }
                R.id.add_target_fragment -> {
                    fragment = AddTargetFragment()
                }
                R.id.list_target_fragment -> {
                    fragment = ListTargetFragment()
                }
            }
            if (fragment == null)
                fragment = MainTargetFragment()
            replaceFragment(fragment)
            true
        }
        bottomNavigationMenu.selectedItemId = R.id.main_target_fragment
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun restoreTargets() {
        var dateTime = CmnFuncClass()
        target.targetList.add(
            TargetClass(
                "Измени образ мышления и ты именишь свою жизнь.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Что имеешь приносит выгоду, что не имеешь - пользу.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Путь воина - это гармония между действиями и решениями.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Готовность пожертвовать собой ради выполнения долга - есть основа поддержания жизни.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Чтобы Тебя заметили Боги, нужно быть впереди.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Выбрал свой путь — иди по нему до конца.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Ставьте перед собой грандиозные цели и тогда все повседневные трудности, встречающиеся на вашем пути, вы преодолеете с легкостью!",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(TargetClass("Цель всякой жизни есть смерть.", dateTime.getDayTime()))
        target.targetList.add(
            TargetClass(
                "Конечная цель любой деятельности человека – достижение покоя.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Измени образ мышления и ты именишь свою жизнь.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Что имеешь приносит выгоду, что не имеешь - пользу.",
                dateTime.getDayTime()
            )
        )
        target.targetList.add(
            TargetClass(
                "Путь воина - это гармония между действиями и решениями.",
                dateTime.getDayTime()
            )
        )
    }

}