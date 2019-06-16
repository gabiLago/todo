package io.keepcoding.tareas.presentation.tasks


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.detail_task.DetailTaskActivity
import io.keepcoding.util.EqualSpacingItemDecoration
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {

    val adapter: TasksAdapter by lazy {
        TasksAdapter {
            tasksViewModel.toggleFinished(it)
        }
    }

    val tasksViewModel: TasksViewModel by viewModel()

    private val onItemClickListener = object : TasksAdapter.OnItemClickListener {
        override fun onItemClick(view: View, task: Task) {

            val intent = Intent(view.context, DetailTaskActivity::class.java)
            intent.putExtra("id", task.id.toString() )
            startActivity(intent)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        bindState()
        adapter.setOnItemClickListener(onItemClickListener)
    }


    private fun setUpRecycler() {
        with (tasksRecycler) {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = this@TasksFragment.adapter
            addItemDecoration(EqualSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.card_margin)))
        }
    }

    private fun bindState() {
        with (tasksViewModel) {
            observe(isLoadingState) {
                onLoadingState(it)
            }
            observe(tasksState) {
                onTasksLoaded(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.loadTasks()
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksLoading.setVisible(isLoading)
    }

    private fun onTasksLoaded(tasks: List<Task>) {
        adapter.submitList(tasks)
    }

}