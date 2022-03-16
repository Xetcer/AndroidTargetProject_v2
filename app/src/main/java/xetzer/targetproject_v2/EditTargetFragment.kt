package xetzer.targetproject_v2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import xetzer.targetproject_v2.viewModel.SharedViewModel


class EditTargetFragment : Fragment() {
    private lateinit var targetEditText: EditText
    private lateinit var editTargetButton: Button
    private lateinit var deleteTargetButton: Button
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_target, container, false)
        targetEditText = view.findViewById(R.id.target_edit_text)
        editTargetButton = view.findViewById(R.id.edit_target_button)
        deleteTargetButton = view.findViewById(R.id.delete_target_button)
        editTargetButton.setOnClickListener {
            // вернемся на предыдущий фрагмент
            sharedViewModel.editTarget?.let {
                sharedViewModel.updateTarget(it)
            }
            parentFragmentManager.popBackStackImmediate()
        }
//        deleteTargetButton.setOnClickListener {
//            sharedViewModel.editTarget?.let {
//                sharedViewModel.deleteTarget(it)
//            }
//            parentFragmentManager.popBackStackImmediate()
//        }
        val text = sharedViewModel.editTarget?.target
        targetEditText.text = Editable.Factory.getInstance().newEditable(text)
        return view
    }

    override fun onStart() {
        super.onStart()
        val targetWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.editTarget?.target = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        targetEditText.addTextChangedListener(targetWatcher)
    }
}