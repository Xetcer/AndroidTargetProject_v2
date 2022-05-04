package xetzer.targetproject_v2.viewModel

import androidx.lifecycle.ViewModel
import xetzer.targetproject_v2.TargetClass

class MainTargetFragmentViewModel : ViewModel() {
    var loadedTarget : TargetClass = TargetClass()
    set(newTarget){
        isTargetLoaded = true
        field = newTarget
    }

    var isTargetLoaded = false
}