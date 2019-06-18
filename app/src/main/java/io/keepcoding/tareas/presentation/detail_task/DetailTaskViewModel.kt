package io.keepcoding.tareas.presentation.detail_task

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.tareas.presentation.tasks.TasksFragment
import io.keepcoding.util.DispatcherFactory
import io.keepcoding.util.Event
import io.keepcoding.util.extensions.call
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailTaskViewModel(

    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
) : BaseViewModel(dispatcherFactory) {

    val taskState = MutableLiveData<Task>()
    val closeAction = MutableLiveData<Event<Unit>>()


    fun loadTask(id: Long) {
        launch {

            val result = withContext(dispatcherFactory.getIO()) { taskRepository.getTaskById(id) }
            taskState.value = result

        }

    }

    fun toggleFinished(task: Task) {
        val newTask = task.copy(isFinished = !task.isFinished)
        launch(dispatcherFactory.getIO()) {
            taskRepository.updateTask(newTask)
        }
    }

    fun deleteTask(task: Task) {
        launch(dispatcherFactory.getIO()) {
            taskRepository.deleteTask(task)

        }
        closeAction.call()


    }
}