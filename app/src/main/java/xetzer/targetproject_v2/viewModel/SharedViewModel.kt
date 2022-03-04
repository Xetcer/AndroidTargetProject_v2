package xetzer.targetproject_v2.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import xetzer.targetproject_v2.TargetClass
import xetzer.targetproject_v2.TargetRepository

class SharedViewModel : ViewModel() {
    private val targetRepository = TargetRepository.getInstance()
    var targetList: MutableList<TargetClass> = mutableListOf()
    fun addTarget(target: TargetClass) {
        targetRepository.addTarget(target)
    }

    fun getTargets(viewLifecycleOwner: LifecycleOwner, ) {
        val targetListLiveData = targetRepository.getTargets()
        targetListLiveData.observe(
            viewLifecycleOwner
        ) { targets ->
            targets?.let {
                this.targetList = targets
            }
        }
    }
}
