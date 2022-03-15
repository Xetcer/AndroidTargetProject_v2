package xetzer.targetproject_v2.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import xetzer.targetproject_v2.TargetClass
import xetzer.targetproject_v2.TargetRepository

class SharedViewModel : ViewModel() {
    private val targetRepository = TargetRepository.getInstance()
    var targetList: MutableList<TargetClass> = mutableListOf()
    var targetListTst : LiveData<MutableList<TargetClass>> ? = null
    var editTarget: TargetClass? = null

    fun addTarget(target: TargetClass) {
        targetRepository.addTarget(target)
    }

    fun getTargets(viewLifecycleOwner: LifecycleOwner) {
        val targetListLiveData = targetRepository.getTargets()
        targetListLiveData.observe(
            viewLifecycleOwner
        ) { targets ->
            targets?.let {
                this.targetList = targets
            }
        }
    }
    fun getTargetsTest(viewLifecycleOwner: LifecycleOwner): LiveData<MutableList<TargetClass>> ? {
        targetListTst = targetRepository.getTargets()
        targetListTst?.observe(
            viewLifecycleOwner
        ) { targets ->
            targets?.let {
                this.targetList = targets
            }
        }
        return targetListTst
    }

    fun updateTarget(target:TargetClass){
        targetRepository.updateTarget(target)
    }

    fun deleteTarget(target: TargetClass){
        targetRepository.deleteTarget(target)
    }
}
