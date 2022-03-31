package xetzer.targetproject_v2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import xetzer.targetproject_v2.TargetClass
import xetzer.targetproject_v2.TargetRepository
import java.io.File

class SharedViewModel : ViewModel() {
    private val targetRepository = TargetRepository.getInstance()
    var targetList = targetRepository.getTargets()
    var targetListTst : LiveData<MutableList<TargetClass>> ? = null
    var editTarget: TargetClass? = null

    fun deleteBD(){
        targetRepository.deleteDataBase()
    }

    fun addTarget(target: TargetClass) {
        targetRepository.addTarget(target)
    }

    fun updateTarget(target:TargetClass){
        targetRepository.updateTarget(target)
    }

    fun deleteTarget(target: TargetClass){
        targetRepository.deleteTarget(target)
    }

    fun getPhotoFile (target: TargetClass) : File {
        return targetRepository.getPhotoFile(target)
    }
}
