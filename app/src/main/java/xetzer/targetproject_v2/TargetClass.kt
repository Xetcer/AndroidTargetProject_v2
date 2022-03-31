package xetzer.targetproject_v2

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TargetClass(
    var target: String = "",       // Цель
    var dateTime: String = ""      // Дата создания в формате д.м.г ч.м
) {
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
    // Фотография цели
    val photoFileName
        get() = "IMG_$id.jpg"
}