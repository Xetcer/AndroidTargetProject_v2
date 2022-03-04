package xetzer.targetproject_v2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xetzer.targetproject_v2.viewModel.SharedViewModel

const val RESTORE_TAG = "RestoreTag"

class MainTargetFragment : Fragment() {
    lateinit var closeApp: FloatingActionButton
    lateinit var shareButton: FloatingActionButton
    lateinit var targetView: TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setRetainInstance(true)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_target, container, false)
        val targetRepository = TargetRepository.getInstance()
        val targetListLiveData = targetRepository.getTargets()
//        val target = TargetRepository.getInstance()
        var dateTime = CmnFuncClass()
        targetView = view.findViewById(R.id.currentTarget_textView)

        if (savedInstanceState != null) {
            var message: String? = savedInstanceState.getString(RESTORE_TAG)
            targetView.text = message
        } else {
            sharedViewModel.getTargets(viewLifecycleOwner)
            targetView.text =
                if (sharedViewModel.targetList.size > 0) sharedViewModel.targetList.random().target
                else ""
//            targetListLiveData.observe(viewLifecycleOwner, Observer { targets ->
//                targets?.let {
//                    sharedViewModel.targetList = targets
//                    targetView.text =
//                        if (sharedViewModel.targetList.size > 0) sharedViewModel.targetList.random().target
//                        else ""
//                }
//            })


        }
        // отправим в другое приложение
        shareButton = view.findViewById(R.id.shareTarget_btn)
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, targetView.text.toString())
            intent.setType("text/plain")
            startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)))
        }

        closeApp = view.findViewById(R.id.closeApp_btn)
        closeApp.setOnClickListener {
            activity?.finish()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RESTORE_TAG, targetView.text.toString())
    }


}