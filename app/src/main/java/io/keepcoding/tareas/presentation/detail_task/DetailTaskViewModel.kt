package io.keepcoding.tareas.presentation.detail_task

import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailTaskViewModel(

    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
) : BaseViewModel(dispatcherFactory) {

    val taskState = MutableLiveData<Task>()


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
}