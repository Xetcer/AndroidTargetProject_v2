package xetzer.targetproject_v2

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import java.util.*
const val TYPED_TEXT_TAG = "TepedText"
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
        if (savedInstanceState != null){
            addTargetEditText.setText(savedInstanceState.getString(TYPED_TEXT_TAG))
        }
        addTargetEditText.setOnEditorActionListener() { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var dateTime = CmnFuncClass()
                // скрыть клавиатуру после
                val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                var rptCounter = 0
                for (item in target.targetList){
                    if (item.target == addTargetEditText.text.toString()) {
                        rptCounter++
                        break
                    }
                }
                if (rptCounter==0) {
                    target.targetList.add(
                        TargetClass(
                            addTargetEditText.text.toString(),
                            dateTime.getDayTime()
                        )
                    )
                    Toast.makeText(context, getString(R.string.new_target_is_add), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, getString(R.string.target_is_rpt), Toast.LENGTH_SHORT).show()
                }
                addTargetEditText.text.clear()
                true
            } else {
                false
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TYPED_TEXT_TAG, addTargetEditText.text.toString())
    }
}