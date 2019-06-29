package io.keepcoding.tareas.presentation.task.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.task.TaskViewModel
import io.keepcoding.util.StrikeThrough
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class DetailTaskFragment : Fragment() {

    val taskViewModel: TaskViewModel by viewModel()
    private lateinit var editListener: OnEditSelected

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnEditSelected) {
            editListener = context
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnEditSelected."
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)


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
                bindEvents()
            }
        }
    }

    private fun onTaskLoaded(task: Task) {

        val formatter = DateTimeFormatter
            .ofPattern("dd-MM-yy")
            .withZone(ZoneId.of("UTC"))

        with(task) {
            createdAtTextView.text = formatter.format(createdAt)
            contentTextView.text = content
            isFinishedCheckbox.isChecked = isFinished
        }

        if (task.isFinished) {
            StrikeThrough.applyStrikeThrough(contentTextView, task.content)
        } else {
            StrikeThrough.removeStrikeThrough(contentTextView, task.content)
        }

        prioritySwitcher(task.isHighPriority)

    }


    private fun bindActions(task: Task) {
        deleteTask.setOnClickListener {

            Snackbar.make(it, "Vas a borrar una tarea", Snackbar.LENGTH_LONG)
                .setAction("CONFIRMAR") {
                    taskViewModel.deleteTask(task)
                    Toast.makeText(it.context, "Tarea borrada", Toast.LENGTH_LONG).show()
                }
                .show()


        }

        editTask.setOnClickListener {
            if(isFinishedCheckbox.isChecked) {
                taskViewModel.toggleFinished(task)
                Toast.makeText(it.context, "Si editas la tarea ya no estará finalizada", Toast.LENGTH_LONG).show()
            }
            editListener.onEditSelected(task)
        }

        isFinishedCheckbox.setOnClickListener {
            taskViewModel.toggleFinished(task)

            if (isFinishedCheckbox.isChecked) {
                StrikeThrough.applyStrikeThrough(contentTextView, task.content, animate = true)
            } else {
                StrikeThrough.removeStrikeThrough(contentTextView, task.content, animate = true)
            }
        }
    }

    private fun bindEvents() {
        with (taskViewModel) {
            observe(closeAction) {
                it.consume {
                    onClose()
                }
            }
        }
    }


    private fun onClose() {
        requireActivity().finish()
    }

    private fun prioritySwitcher(state: Boolean){
        if (state) {
            isHighPriorityCheckbox.setImageResource(R.drawable.ic_star_on)
        }  else {
            isHighPriorityCheckbox.setImageDrawable(null)
        }
    }

    interface OnEditSelected {
        fun onEditSelected(task: Task)
    }


}