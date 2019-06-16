package io.keepcoding.tareas.presentation.detail_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class DetailTaskFragment : Fragment() {

    val detailTaskViewModel: DetailTaskViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id", "")
        detailTaskViewModel.loadTask(id!!.toLong())

        bindState()

    }

    private fun bindState() {
        with(detailTaskViewModel) {

            observe(taskState) {
                onTaskLoaded(it)
            }
        }
    }

    private fun onTaskLoaded(task: Task) {

        val formatter = DateTimeFormatter
            .ofPattern("dd-MM-yy")
            .withZone(ZoneId.of("UTC"))

        with(task) {
            detailCreatedAt.text = formatter.format(createdAt)
            detailContent.text = content
            detailIsFinished.isChecked = isFinished
            }

            detailIsFinished.setOnClickListener {

            detailTaskViewModel.toggleFinished(task)


        }

    }


}