package xetzer.targetproject_v2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xetzer.targetproject_v2.TargetClass

@Database(entities = [TargetClass::class], version = 1)
abstract class TargetsDataBase : RoomDatabase() {
    abstract fun targetDao(): TargetDAO
}