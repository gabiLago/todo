package io.keepcoding.tareas.presentation.tasks



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.TasksViewUtils
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.item_task_high_priority.view.*
import kotlinx.android.synthetic.main.item_task_date.view.*
import kotlinx.android.synthetic.main.item_task.view.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class TasksAdapter(
    private val onFinished: (task: Task) -> Unit
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {

    lateinit var taskClickListener: OnTaskClickListener
    lateinit var editClickListener: OnEditClickListener
    lateinit var deleteClickListener: OnDeleteClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }




    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) = taskClickListener.onItemClick(itemView, getItem(adapterPosition))

        fun bind(task: Task) {
            with(itemView) {

                cardContentText.text = task.content

                val formatter = DateTimeFormatter
                    .ofPattern("dd-MM-yy")
                    .withZone(ZoneId.of("UTC"))

                createdAtDate.text = formatter.format(task.createdAt)

                TasksViewUtils.prioritySwitcher(task.isHighPriority, this)

                taskFinishedCheck.isChecked = task.isFinished

                if (task.isFinished) {
                    TasksViewUtils.applyStrikeThrough(cardContentText, task.content)
                } else {
                    TasksViewUtils.removeStrikeThrough(cardContentText, task.content)
                }

                taskFinishedCheck.setOnClickListener {
                    onFinished(task)

                    if (taskFinishedCheck.isChecked) {
                        TasksViewUtils.applyStrikeThrough(cardContentText, task.content, animate = true)
                    } else {
                        TasksViewUtils.removeStrikeThrough(cardContentText, task.content, animate = true)
                    }
                }

                editTask.setOnClickListener{
                    editClickListener.onEditClick(it, task)
                }

                deleteTask.setOnClickListener{
                    deleteClickListener.onDeleteClick(it, task)
                }

            }

        }

    }


    fun refreshDataSet() {
        notifyDataSetChanged()
    }

    interface OnTaskClickListener {
        fun onItemClick(view: View, task: Task)
    }

    interface OnEditClickListener {
        fun onEditClick(view: View, task: Task)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(view: View, task: Task)
    }

    fun setOnItemClickListener(itemClickListener: OnTaskClickListener) {
        this.taskClickListener = itemClickListener
    }

    fun setOnEditClickListener(editClickListener: OnEditClickListener) {
        this.editClickListener = editClickListener
    }

    fun setOnDeleteClickListener(deleteClickListener: OnDeleteClickListener) {
        this.deleteClickListener = deleteClickListener
    }

}