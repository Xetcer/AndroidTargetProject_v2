package xetzer.targetproject_v2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

const val RESTORE_TAG = "RestoreTag"

class MainTargetFragment : Fragment() {
    lateinit var closeApp: FloatingActionButton
    lateinit var shareButton: FloatingActionButton
    lateinit var targetView: TextView

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
        val target = MyDataAppClass.getInstance()
        var dateTime = CmnFuncClass()
        targetView = view.findViewById(R.id.currentTarget_textView)

        if (savedInstanceState != null) {
            var message: String? = savedInstanceState.getString(RESTORE_TAG)
            targetView.text = message
        } else {
            targetView.text = target.targetList.random().target
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