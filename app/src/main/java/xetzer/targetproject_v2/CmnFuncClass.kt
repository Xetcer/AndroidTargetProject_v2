package xetzer.targetproject_v2

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CmnFuncClass {
    fun getDayTime(): String {
        val dayTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            date.format(formatter)
        } else {
            val date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            formatter.format(date)
        }
        return dayTime
    }
}