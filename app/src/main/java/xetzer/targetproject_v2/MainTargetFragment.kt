package xetzer.targetproject_v2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xetzer.targetproject_v2.viewModel.SharedViewModel

const val RESTORE_TAG = "RestoreTag"

class MainTargetFragment : Fragment() {
    private lateinit var closeApp: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var targetView: TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_target, container, false)

//        sharedViewModel.getTargets(viewLifecycleOwner)

        targetView = view.findViewById(R.id.currentTarget_textView)


//        if (savedInstanceState != null) {
//            val message: String? = savedInstanceState.getString(RESTORE_TAG)
//            targetView.text = message
//        } else {
//            targetView.text = "Loading targets"
//        }

        sharedViewModel.getTargetsTest(viewLifecycleOwner)
        if ( savedInstanceState != null){
            val message: String? = savedInstanceState.getString(RESTORE_TAG)
            targetView.text = message
        }else{
            // Обновление значений из базы данных в режиме реального времени.
            sharedViewModel.targetListTst?.observe(viewLifecycleOwner) { targets ->
                if (targets.size > 0) {
                    targetView.text = targets.random().target
                } else {
                    targetView.text = R.string.loadingTarget.toString()
                }
            }
        }

        // отправим в другое приложение
        shareButton = view.findViewById(R.id.shareTarget_btn)
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, targetView.text.toString())
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
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