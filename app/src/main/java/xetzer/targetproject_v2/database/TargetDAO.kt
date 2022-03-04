package xetzer.targetproject_v2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import xetzer.targetproject_v2.TargetClass
import java.util.*

@Dao
interface TargetDAO {
    @Query ("SELECT * FROM TargetClass")
    fun getAllTargets() : LiveData<MutableList<TargetClass>>
    @Query ("SELECT * FROM TargetClass WHERE id=(:id)" )
    fun getTarget(id: UUID) : LiveData<TargetClass?>
    @Update
    fun updateTarget(target: TargetClass)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTarget(target : TargetClass)
    @Delete
    fun delete(target: TargetClass)
}