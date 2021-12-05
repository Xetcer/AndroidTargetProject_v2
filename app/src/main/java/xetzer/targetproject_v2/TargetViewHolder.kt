package xetzer.targetproject_v2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TargetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val targetTextView: TextView = itemView.findViewById(R.id.target_text_view)
    private val dateTimeTextView: TextView = itemView.findViewById(R.id.date_time_text_view)
    fun bind(target: TargetClass) {

        targetTextView.text = target.target
        dateTimeTextView.text = target.dateTime

    }
}