package xetzer.targetproject_v2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xetzer.targetproject_v2.databinding.FragmentMainTargetBinding
import xetzer.targetproject_v2.viewModel.MainTargetFragmentViewModel
import xetzer.targetproject_v2.viewModel.MainTargetViewModel
import xetzer.targetproject_v2.viewModel.SharedViewModel

const val RESTORE_TAG = "RestoreTag"

class MainTargetFragment : Fragment() {
    private lateinit var closeApp: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var targetView: TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val mainTargetFragmentViewModel: MainTargetFragmentViewModel by activityViewModels()
    private val mainTargetViewModel = MainTargetViewModel(::targetLoadedCb)

    override fun onStart() {
        super.onStart()
        if (!mainTargetFragmentViewModel.isTargetLoaded) {
            mainTargetViewModel.startTargetObserve(viewLifecycleOwner, sharedViewModel)
        } else {
            mainTargetViewModel.target = mainTargetFragmentViewModel.loadedTarget
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_target, container, false)

        val binding: FragmentMainTargetBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_target, container, false)
        binding.viewModel = mainTargetViewModel
        targetView = view.findViewById(R.id.currentTarget_textView)

//        if ( savedInstanceState != null){
//            val message: String? = savedInstanceState.getString(RESTORE_TAG)
//            targetView.text = message
//        }
//        if (mainTargetViewModel.isTargetLoaded){
//            targetView.text = mainTargetViewModel.target.text
//        }

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
        return binding.root
    }

    private fun targetLoadedCb() {
        mainTargetFragmentViewModel.loadedTarget = mainTargetViewModel.target
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(RESTORE_TAG, targetView.text.toString())
//    }

}