package xetzer.targetproject_v2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListTargetFragment : Fragment() {
    private lateinit var targetRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val target = MyDataAppClass.getInstance()
//        var dateTime = CmnFuncClass()
        // Inflate the layout for this fragment
//        target.targetList.add(TargetClass("Измени образ мышления и ты именишь свою жизнь.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Что имеешь приносит выгоду, что не имеешь - пользу.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Путь воина - это гармония между действиями и решениями.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Готовность пожертвовать собой ради выполнения долга - есть основа поддержания жизни.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Чтобы Тебя заметили Боги, нужно быть впереди.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Выбрал свой путь — иди по нему до конца.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Ставьте перед собой грандиозные цели и тогда все повседневные трудности, встречающиеся на вашем пути, вы преодолеете с легкостью!", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Цель всякой жизни есть смерть.", dateTime.getDayTime()))
//        target.targetList.add(TargetClass("Конечная цель любой деятельности человека – достижение покоя.", dateTime.getDayTime()))

        val view = inflater.inflate(R.layout.fragment_list_target, container, false)
        targetRecyclerView = view.findViewById(R.id.target_recycler_view)
        targetRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        targetRecyclerView.adapter = TargetAdapter(target.targetList)
        targetRecyclerView.addItemDecoration(DividerItemDecoration(targetRecyclerView.context, DividerItemDecoration.VERTICAL))
        return view
    }
}