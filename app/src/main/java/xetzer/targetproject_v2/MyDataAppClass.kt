package xetzer.targetproject_v2

import android.app.Application

class MyDataAppClass:Application() {
    var targetList: MutableList<TargetClass> = mutableListOf()
    companion object{
        private var INSTANCE: MyDataAppClass ? =null
        fun getInstance():MyDataAppClass{
            INSTANCE = MyDataAppClass()
            return INSTANCE!!
        }
    }
}