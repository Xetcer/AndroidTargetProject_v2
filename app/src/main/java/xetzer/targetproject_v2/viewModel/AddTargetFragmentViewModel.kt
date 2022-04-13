package xetzer.targetproject_v2.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import xetzer.targetproject_v2.TargetClass
import java.io.File

class AddTargetFragmentViewModel : ViewModel() {
    lateinit var photoFile: File
    lateinit var photoUri: Uri
    lateinit var target: TargetClass
    var isTargetCreated : Boolean = false
}