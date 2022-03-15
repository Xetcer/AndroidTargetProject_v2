package xetzer.targetproject_v2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xetzer.targetproject_v2.viewModel.SharedViewModel

class ListTargetFragment : Fragment() {
    private lateinit var targetRecyclerView: RecyclerView
    private var callbacks: TargetListCallback? = null
    private val sharedViewModel : SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        sharedViewModel.getTargets(viewLifecycleOwner)
        val view = inflater.inflate(R.layout.fragment_list_target, container, false)
        targetRecyclerView = view.findViewById(R.id.target_recycler_view)
        targetRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        targetRecyclerView.adapter = TargetAdapter(sharedViewModel.targetList){target->
            sharedViewModel.editTarget = target
            callbacks?.onTargetSelected(target)
        }
        targetRecyclerView.addItemDecoration(DividerItemDecoration(targetRecyclerView.context, DividerItemDecoration.VERTICAL))
        return view
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