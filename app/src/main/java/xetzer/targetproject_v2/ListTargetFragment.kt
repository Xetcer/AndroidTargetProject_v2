package xetzer.targetproject_v2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xetzer.targetproject_v2.viewModel.SharedViewModel

class ListTargetFragment : Fragment() {
    private lateinit var targetRecyclerView: RecyclerView
    private var callbacks: TargetListCallback? = null
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private var adapter: TargetAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_target, container, false)
        targetRecyclerView = view.findViewById(R.id.target_recycler_view)
        targetRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        targetRecyclerView.adapter = adapter
        targetRecyclerView.addItemDecoration(DividerItemDecoration(targetRecyclerView.context, DividerItemDecoration.VERTICAL))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.targetList.observe(viewLifecycleOwner) { targets ->
            targets?.let { updateList(targets) }
        }
    }

    private fun updateList(targets : List<TargetClass>){
        adapter = TargetAdapter(targets, editTarget, deleteTarget)
        targetRecyclerView.adapter = adapter
    }
    // Лямбда обработчик кнопки изменить на ViewHolder
    private val editTarget : EditTarget= {
        target: TargetClass ->
        sharedViewModel.editTarget = target
        callbacks?.onTargetSelected(target)
    }
    // Лямбда обработчик кнопки удалить на ViewHolder
    private val deleteTarget : EditTarget = {
        target : TargetClass ->
        sharedViewModel.deleteTarget(target)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as TargetListCallback?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}
// Тип лямбда функций
typealias EditTarget = (TargetClass) -> Unit