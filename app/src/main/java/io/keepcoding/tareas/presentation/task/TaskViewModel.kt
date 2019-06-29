package io.keepcoding.tareas.presentation.task


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory
import io.keepcoding.util.Event
import io.keepcoding.util.extensions.call
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant


class TaskViewModel(

    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
) : BaseViewModel(dispatcherFactory) {

    val taskState = MutableLiveData<Task>()
    val closeAction = MutableLiveData<Event<Unit>>()

    fun save(content: String, isHighPriority: Boolean) {
        if (!validateContent(content)) {
            return
        }

        launch {
            withContext(dispatcherFactory.getIO()) {
                taskRepository.addTask(Task(0, content, Instant.now(), isHighPriority, false))
            }
            closeAction.call()
        }
    }

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


    fun updateTask(task: Task, content: String, isHighPriority: Boolean) {
        val updatedTask = task.copy(content = content, isHighPriority = isHighPriority)
        if (!validateContent(content)) {
            return
        }

        launch {
            withContext(dispatcherFactory.getIO()) {
                taskRepository.updateTask(updatedTask)
            }
        }


    }

    private fun validateContent(content: String): Boolean =
        content.isNotEmpty()


    fun goBackOnFragmentStack(fragmentActivity: FragmentActivity){
        val fragment = fragmentActivity.getSupportFragmentManager()
        fragment.popBackStack()
    }

}