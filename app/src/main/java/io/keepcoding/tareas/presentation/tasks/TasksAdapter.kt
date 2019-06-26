package io.keepcoding.tareas.presentation.tasks

import android.animation.ValueAnimator
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.detail_task.DetailTaskActivity
import kotlinx.android.synthetic.main.item_task.view.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class TasksAdapter(
    private val onFinished: (task: Task) -> Unit
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener{
                val task = getItem(adapterPosition)
                val intent = Intent(view.context, DetailTaskActivity::class.java)
                intent.putExtra("id", task.id )
                itemView.context.startActivity(intent)
                }
        }

        fun bind(task: Task) {
            with(itemView) {

                cardContentText.text = task.content

                val formatter = DateTimeFormatter
                    .ofPattern("dd-MM-yy")
                    .withZone(ZoneId.of("UTC"))

                cardCreatedAtDate.text = formatter.format(task.createdAt)


                val imgHighPriorityOn = R.drawable.ic_star_on

                if (task.isHighPriority) {
                    cardIsHighPriority.setImageResource(imgHighPriorityOn)
                }

                taskFinishedCheck.isChecked = task.isFinished

                if (task.isFinished) {
                    applyStrikeThrough(cardContentText, task.content)
                } else {
                    removeStrikeThrough(cardContentText, task.content)
                }

                taskFinishedCheck.setOnClickListener {
                    onFinished(task)

                    if (taskFinishedCheck.isChecked) {
                        applyStrikeThrough(cardContentText, task.content, animate = true)
                    } else {
                        removeStrikeThrough(cardContentText, task.content, animate = true)
                    }
                }


            }


        }

        private fun applyStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animate) {
                ValueAnimator.ofInt(content.length).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                view.text = span
            }
        }

        private fun removeStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animate) {
                ValueAnimator.ofInt(content.length, 0).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                view.text = content
            }
        }


    }

    fun refreshDataSet() {
        notifyDataSetChanged()
    }


}