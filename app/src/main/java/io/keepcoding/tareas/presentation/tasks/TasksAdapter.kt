package io.keepcoding.tareas.presentation.tasks

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.StrikeThrough
import kotlinx.android.synthetic.main.item_task.view.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class TasksAdapter(
    private val onFinished: (task: Task) -> Unit
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {

    lateinit var taskClickListener: OnTaskClickListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnTaskClickListener {
        fun onItemClick(view: View, task: Task)
    }

    fun setOnItemClickListener(itemClickListener: OnTaskClickListener) {
        this.taskClickListener = itemClickListener
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

                cardCreatedAtDate.text = formatter.format(task.createdAt)

                prioritySwitcher(task.isHighPriority, this)

                taskFinishedCheck.isChecked = task.isFinished

                if (task.isFinished) {
                    StrikeThrough.applyStrikeThrough(cardContentText, task.content)
                } else {
                    StrikeThrough.removeStrikeThrough(cardContentText, task.content)
                }

                taskFinishedCheck.setOnClickListener {
                    onFinished(task)

                    if (taskFinishedCheck.isChecked) {
                        StrikeThrough.applyStrikeThrough(cardContentText, task.content, animate = true)
                    } else {
                        StrikeThrough.removeStrikeThrough(cardContentText, task.content, animate = true)
                    }
                }

            }


        }




    }



    fun refreshDataSet() {
        notifyDataSetChanged()
    }

    private fun prioritySwitcher(state: Boolean, view: View){
        if (state) {
            view.cardIsHighPriority.setImageResource(R.drawable.ic_star_on)
        }  else {
            view.cardIsHighPriority.setImageDrawable(null)
        }
    }

}