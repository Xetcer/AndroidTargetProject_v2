package xetzer.targetproject_v2

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import xetzer.targetproject_v2.viewModel.SharedViewModel

const val TYPED_TEXT_TAG = "TypedText"

class AddTargetFragment : Fragment() {
    private lateinit var addTargetEditText: EditText
    private lateinit var deleteTargetsButton:Button
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_target, container, false)
        addTargetEditText = view.findViewById(R.id.addTarget_editText)
        addTargetEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        deleteTargetsButton = view.findViewById(R.id.delete_all_targets_button)
        deleteTargetsButton.setOnClickListener {
            sharedViewModel.deleteBD()
        }

        if (savedInstanceState != null) {
            addTargetEditText.setText(savedInstanceState.getString(TYPED_TEXT_TAG))
        }
        addTargetEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val dateTime = CmnFuncClass()
                // скрыть клавиатуру после
                val inputMethodManager =
                    context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                var rptCounter = 0
                sharedViewModel.targetList.observe(viewLifecycleOwner) { targets ->
                    targets?.let {
                        for (item in it) {
                            if (item.target == addTargetEditText.text.toString()) {
                                rptCounter++
                                break
                            }
                        }
                    }
                }
                if (rptCounter == 0) {
                    val target = TargetClass(
                        addTargetEditText.text.toString(),
                        dateTime.getDayTime()
                    )
                    sharedViewModel.addTarget(target)
                    Toast.makeText(
                        context,
                        getString(R.string.new_target_is_add),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, getString(R.string.target_is_rpt), Toast.LENGTH_SHORT)
                        .show()
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