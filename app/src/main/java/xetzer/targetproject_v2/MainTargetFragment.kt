package xetzer.targetproject_v2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainTargetFragment : Fragment() {
    lateinit var closeApp: FloatingActionButton
    lateinit var targetView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_target, container, false)
        val target = MyDataAppClass.getInstance()
        var dateTime = CmnFuncClass()
        target.targetList.add(TargetClass("Измени образ мышления и ты именишь свою жизнь.", dateTime.getDayTime()))
        target.targetList.add(TargetClass("Что имеешь приносит выгоду, что не имеешь - пользу.", dateTime.getDayTime()))
        target.targetList.add(TargetClass("Путь воина - это гармония между действиями и решениями.", dateTime.getDayTime()))

        targetView = view.findViewById(R.id.currentTarget_textView)

//        val cTarget =  target.targetList.random()
        targetView.text = target.targetList.random().target

        closeApp = view.findViewById(R.id.closeApp_btn)
        closeApp.setOnClickListener {
            activity?.finish()
        }
        return view
    }
}