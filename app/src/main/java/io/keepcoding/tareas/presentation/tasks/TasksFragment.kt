package io.keepcoding.tareas.presentation.tasks


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.task.detail.DetailTaskActivity
import io.keepcoding.tareas.presentation.task.detail.DetailTaskFragment
import io.keepcoding.tareas.presentation.task.edit.EditTaskActivity
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


    private val onItemClickListener = object : TasksAdapter.OnTaskClickListener {
        override fun onItemClick(view: View, task: Task) {
            val intent = Intent(view.context, DetailTaskActivity::class.java)
            intent.putExtra("id", task.id )
            startActivity(intent)
        }
    }

    private val onEditClickListener = object : TasksAdapter.OnEditClickListener {
        override fun onEditClick(view: View, task: Task) {
            val intent = Intent(view.context, EditTaskActivity::class.java)
            intent.putExtra("id", task.id )
            startActivity(intent)
        }
    }

    private val onDeleteClickListener = object : TasksAdapter.OnDeleteClickListener {
        override fun onDeleteClick(view: View, task: Task) {



            Snackbar.make(view, "Vas a borrar una tarea", Snackbar.LENGTH_LONG)
                .setAction("CONFIRMAR") {
                    tasksViewModel.deleteTask(task)
                    Toast.makeText(view.context, "Tarea borrada", Toast.LENGTH_LONG).show()
                }
                .show()
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
        adapter.setOnEditClickListener(onEditClickListener)
        adapter.setOnDeleteClickListener(onDeleteClickListener)
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

            observe(deleteState){
                deleteTask()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.loadTasks()
        adapter.refreshDataSet()
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksLoading.setVisible(isLoading)
    }

    private fun onTasksLoaded(tasks: List<Task>) {
        adapter.submitList(tasks)
    }

    private fun deleteTask() {
        tasksViewModel.loadTasks()
        adapter.refreshDataSet()
    }
}