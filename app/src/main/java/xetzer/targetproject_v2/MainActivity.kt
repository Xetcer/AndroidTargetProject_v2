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

const val LAST_SELECTED_BOT_MENU_TAG = "BottomSelectedMenu"
val MAIN_TARGET_FRAGMENT = MainTargetFragment().javaClass.name
val ADD_TARGET_FRAGMENT = AddTargetFragment().javaClass.name
val LIST_TARGET_FRAGMENT = ListTargetFragment().javaClass.name

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
                    if (savedInstanceState != null) {
                        fragment = supportFragmentManager.getFragment(
                            savedInstanceState,
                            MAIN_TARGET_FRAGMENT
                        )
                    }
                    if (fragment == null)
                        fragment = MainTargetFragment()
                }
                R.id.add_target_fragment -> {
                    if (savedInstanceState != null) {
                        fragment = supportFragmentManager.getFragment(
                            savedInstanceState,
                            ADD_TARGET_FRAGMENT
                        )
                    }
                    if (fragment == null)
                        fragment = AddTargetFragment()
                }
                R.id.list_target_fragment -> {
                    if (savedInstanceState != null) {
                        fragment = supportFragmentManager.getFragment(
                            savedInstanceState,
                            LIST_TARGET_FRAGMENT
                        )
                    }
                    if (fragment == null)
                        fragment = ListTargetFragment()
                }
            }
            if (fragment == null)
                fragment = MainTargetFragment()
            replaceFragment(fragment)
            true
        }
        bottomNavigationMenu.selectedItemId =
            savedInstanceState?.getInt(LAST_SELECTED_BOT_MENU_TAG) ?: R.id.main_target_fragment
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_BOT_MENU_TAG, bottomNavigationMenu.selectedItemId)
        val currentFragment = supportFragmentManager.fragments.last()
        supportFragmentManager.putFragment(
            outState,
            currentFragment.javaClass.name,
            currentFragment
        )
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