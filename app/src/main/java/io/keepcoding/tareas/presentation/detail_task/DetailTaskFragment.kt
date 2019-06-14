package io.keepcoding.tareas.presentation.detail_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import kotlinx.android.synthetic.main.activity_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTaskFragment : Fragment() {

    val detailTaskViewModel: DetailTaskViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id", "")

        val getTask = detailTaskViewModel.loadTask(id!!.toLong())

        detailContent.text = getTask.content
    }



}