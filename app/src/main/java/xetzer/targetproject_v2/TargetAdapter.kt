package xetzer.targetproject_v2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TargetAdapter(
    private val targetList: List<TargetClass>,
    private val editListener: (TargetClass) -> Unit,
    private val deleteListener: (TargetClass) -> Unit
) :
    RecyclerView.Adapter<TargetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.target_card_list_item, parent, false)
        return TargetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) {
        val target = targetList[position]
        holder.bind(target, editListener, deleteListener)
//        holder.itemView.setOnClickListener { listener(target) }
    }

    override fun getItemCount(): Int {
        return targetList.size
    }
}