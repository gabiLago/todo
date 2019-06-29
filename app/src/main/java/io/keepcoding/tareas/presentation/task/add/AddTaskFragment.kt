package io.keepcoding.tareas.presentation.task.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.presentation.task.TaskViewModel
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddTaskFragment : Fragment() {

    val taskViewModel: TaskViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        bindActions()
    }

    private fun bindActions() {
        saveButton.setOnClickListener {
            val taskContent = contentTextView.text.toString()
            val isHighPriority = isPriorityCheckBox.isChecked
            taskViewModel.save(taskContent, isHighPriority)
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

}