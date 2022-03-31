package xetzer.targetproject_v2

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.io.File

class TargetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val targetTextView: TextView = itemView.findViewById(R.id.target_text_view)
    private val dateTimeTextView: TextView = itemView.findViewById(R.id.target_date_text_view)
    private val editTargetButton: MaterialButton = itemView.findViewById(R.id.edit_target_button)
    private val deleteTargetButton: MaterialButton =
        itemView.findViewById(R.id.delete_target_button)
    private val targetImageView: ImageView = itemView.findViewById(R.id.target_image_view)
    private lateinit var photoFile: File
    private val targetRepository = TargetRepository.getInstance()
    fun bind(
        target: TargetClass,
        editTargetListener: (TargetClass) -> Unit,
        deleteTargetListener: (TargetClass) -> Unit
    ) {
        targetTextView.text = target.target
        dateTimeTextView.text = target.dateTime
        editTargetButton.setOnClickListener {
            editTargetListener(target)
        }
        deleteTargetButton.setOnClickListener {
            deleteTargetListener(target)
        }
        photoFile = targetRepository.getPhotoFile(target)
        if (photoFile.exists()) {
            var pictureUtils = PictureUtils()
            val bitmap = pictureUtils.getScaledBitmap(
                photoFile.path,
                500,
                500
            )
            targetImageView.setImageBitmap(bitmap)
        }
    }
}