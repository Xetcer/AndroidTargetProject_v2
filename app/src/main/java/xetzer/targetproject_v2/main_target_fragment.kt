package xetzer.targetproject_v2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class main_target_fragment : Fragment() {
    lateinit var closeApp: FloatingActionButton
    lateinit var targetView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_target_fragment, container, false)
        val target = MyDataAppClass.getInstance()
        targetView = view.findViewById(R.id.currentTarget_textView)
//        val cTarget =  target.targetList.random()
//        targetView.text = cTarget.target

        closeApp = view.findViewById(R.id.closeApp_btn)
        closeApp.setOnClickListener {
            activity?.finish()
        }
        return view
    }

}