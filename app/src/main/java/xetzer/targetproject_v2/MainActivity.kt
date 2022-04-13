package xetzer.targetproject_v2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import xetzer.targetproject_v2.databinding.FragmentMainTargetBinding

const val LAST_SELECTED_BOT_MENU_TAG = "BottomSelectedMenu"
val MAIN_TARGET_FRAGMENT: String = MainTargetFragment().javaClass.name
val ADD_TARGET_FRAGMENT: String = AddTargetFragment().javaClass.name
val LIST_TARGET_FRAGMENT: String = ListTargetFragment().javaClass.name

class MainActivity : AppCompatActivity(),  TargetListCallback {
    private lateinit var bottomNavigationMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationMenu = findViewById(R.id.bottom_navigation_menu)
        bottomNavigationMenu.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.main_target_fragment -> {
                    try {
                        if (savedInstanceState != null) {
                            fragment = supportFragmentManager.getFragment(
                                savedInstanceState,
                                MAIN_TARGET_FRAGMENT
                            )
                            val binding: FragmentMainTargetBinding = DataBindingUtil.setContentView(this, R.layout.fragment_main_target)
                        }
                    }catch ( ex: Exception){
                        fragment = null
                        Toast.makeText(this, "При смене фрагментов ${ex.toString()}", Toast.LENGTH_LONG).show()
                    }
                    if (fragment == null)
                        fragment = MainTargetFragment()
                }
                R.id.add_target_fragment -> {
                    try {
                        if (savedInstanceState != null) {
                            fragment = supportFragmentManager.getFragment(
                                savedInstanceState,
                                ADD_TARGET_FRAGMENT
                            )
                        }
                    }catch (ex: Exception){
                        fragment = null
                        Toast.makeText(this, "При смене фрагментов ${ex.toString()}", Toast.LENGTH_LONG).show()
                    }
                    if (fragment == null)
                        fragment = AddTargetFragment()
                }
                R.id.list_target_fragment -> {
                    try {
                        if (savedInstanceState != null) {
                            fragment = supportFragmentManager.getFragment(
                                savedInstanceState,
                                LIST_TARGET_FRAGMENT
                            )
                        }
                    }catch (ex: Exception){
                        fragment = null
                        Toast.makeText(this, "При смене фрагментов ${ex.toString()}", Toast.LENGTH_LONG).show()
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

    private fun replaceFragment(fragment: Fragment) {
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

    override fun onTargetSelected(target: TargetClass) {
        val fragment = EditTargetFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}