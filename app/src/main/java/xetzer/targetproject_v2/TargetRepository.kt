package xetzer.targetproject_v2

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import xetzer.targetproject_v2.database.TargetsDataBase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "targets-database"
class TargetRepository private constructor (context: Context) {

    companion object{
        private var INSTANCE: TargetRepository ? = null
        fun initialize(context: Context){
            if (INSTANCE == null) {
                INSTANCE = TargetRepository(context)
            }
        }
        fun getInstance():TargetRepository{
            return INSTANCE ?: throw IllegalStateException("TargetRepository must be initialized")
        }
    }

    private val dataBase : TargetsDataBase = Room.databaseBuilder(
        context.applicationContext,
        TargetsDataBase::class.java,
        DATABASE_NAME
    ).build()

    private val targetDao = dataBase.targetDao()
    private val executor = Executors.newSingleThreadExecutor()
    fun deleteDataBase(){
        executor.execute {
            dataBase.clearAllTables()
        }
    }
    fun getTargets() : LiveData<MutableList<TargetClass>> = targetDao.getAllTargets()
    fun getTarget(id:UUID): LiveData<TargetClass?> = targetDao.getTarget(id)
    fun addTarget(target:TargetClass){
        executor.execute {
            targetDao.insertTarget(target)
        }
    }
    fun updateTarget(target:TargetClass){
        executor.execute {
            targetDao.updateTarget(target)
        }
    }
    fun deleteTarget(target:TargetClass){
        executor.execute {
            targetDao.delete(target)
        }
    }

}

class ApplicationInit : Application(){
    override fun onCreate() {
        super.onCreate()
        TargetRepository.initialize(this)
    }
}