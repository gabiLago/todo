package io.keepcoding.tareas.presentation.task.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.task.TaskViewModel
import io.keepcoding.util.TasksViewUtils
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_edit_task.*
import kotlinx.android.synthetic.main.item_task_date.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class EditTaskFragment : Fragment() {

    val taskViewModel: TaskViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id", 0)
        taskViewModel.loadTask(id!!)

        bindState()

    }

    private fun bindState() {
        with(taskViewModel) {
            observe(taskState) {
                onTaskLoaded(it)
                bindActions(it)
            }
        }
    }


    private fun onTaskLoaded(task: Task) {

        val formatter = DateTimeFormatter
            .ofPattern("dd-MM-yy")
            .withZone(ZoneId.of("UTC"))

        with(task) {
            createdAtDate.text = formatter.format(createdAt)
            contentEditText.setText(content)
            isHighPriorityCheckbox.isChecked = isHighPriority
        }

        TasksViewUtils.prioritySwitcher(isHighPriorityCheckbox.isChecked, this.view!!)

    }

    private fun bindActions(task: Task) {
        saveTaskButton.setOnClickListener {
            val taskContent = contentEditText.text.toString()
            val isHighPriority = isHighPriorityCheckbox.isChecked

            taskViewModel.updateTask(task, taskContent, isHighPriority)


            activity?.run {
                Toast.makeText(it.context, "Tarea editada", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        discardTaskButton.setOnClickListener {
            activity?.run {
                taskViewModel.goBackOnFragmentStack(this)
            }
        }


        isHighPriorityCheckbox.setOnClickListener {
            TasksViewUtils.prioritySwitcher(isHighPriorityCheckbox.isChecked, this.view!!)
        }
    }



}