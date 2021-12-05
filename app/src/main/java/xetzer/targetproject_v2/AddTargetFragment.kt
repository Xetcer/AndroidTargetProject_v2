package xetzer.targetproject_v2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import java.util.*

class AddTargetFragment : Fragment() {
    lateinit var addTargetEditText: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val target = MyDataAppClass.getInstance()
        val view = inflater.inflate(R.layout.fragment_add_target, container, false)
        addTargetEditText = view.findViewById(R.id.addTarget_editText)
        addTargetEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        addTargetEditText.setOnEditorActionListener() { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var dateTime = CmnFuncClass()
                target.targetList.add(TargetClass(addTargetEditText.text.toString(), dateTime.getDayTime()))
                true
            } else {
                false
            }
        }
        return view
    }
}