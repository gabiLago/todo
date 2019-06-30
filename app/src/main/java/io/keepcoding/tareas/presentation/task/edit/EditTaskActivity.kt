package io.keepcoding.tareas.presentation.task.edit

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import kotlinx.android.synthetic.main.activity_main.*

class EditTaskActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        setUpToolbar()
        setUpFragment(savedInstanceState)

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar as Toolbar)
        setTitle(R.string.detail_task_title)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    private fun setUpFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {

            val fragment = EditTaskFragment()
            val id = intent.getLongExtra("id", 0)

            val args = Bundle()
            args.putLong("id", id)

            fragment.arguments = args

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}