package xetzer.targetproject_v2.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import xetzer.targetproject_v2.R
import xetzer.targetproject_v2.TargetClass

class MainTargetViewModel : BaseObservable() {
    // Текущая цель
    @get:Bindable
    var target: TargetClass = TargetClass(target = R.string.loadingTarget.toString())
        set(target) {
            field = target
            notifyChange()
        }

    fun startTargetObserve(lifecycleOwner: LifecycleOwner, sharedViewModel: SharedViewModel) {
        sharedViewModel.targetList.observe(lifecycleOwner) { targets ->
            if (targets.size > 0) {
                target = targets.random()
            }
        }
    }
}