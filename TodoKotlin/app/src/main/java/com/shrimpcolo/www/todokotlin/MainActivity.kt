package com.shrimpcolo.www.todokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.shrimpcolo.www.todokotlin.data.Task
import com.shrimpcolo.www.todokotlin.data.source.local.LocalDataSource
import com.shrimpcolo.www.todokotlin.data.source.local.TaskDataSource
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import rx.Single
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.text = "Hello Kotlin 11"
        hello.textSize = 20.0f

        initViews()
    }

    private fun initViews() {
        btnAddTask.setOnClickListener {
            Log.d(TAG, " Add Task! ")
            TaskDataSource.save(Task("title", "desc"))
        }

        val container = findViewById(R.id.container) as ViewGroup

        btnQueryTask.setOnClickListener {
            Log.d(TAG, " Query Tasks! ")
            TaskDataSource.findAll()
                    .subscribe {
                        Log.d(TAG, " Query Tasks 111 ! ")
                        it.forEach { task ->
                            container.addView(TextView(this@MainActivity).apply {
                                text = task.toString()
                            })
                        }
                    }
        }

        btnQueryOneTask.setOnClickListener {
            Log.d(TAG, " find one task")
            TaskDataSource.findOne("0cfe0fcd-2895-47d7-96a1-bc24ce5e6bc6")
                    .subscribe {
                        Log.d(TAG, " find task: $it")
                    }
        }
    }



    private fun addTasks(task: Task) {

//        realm.rxExecuteTransaction {
//            it.insert(task)
//            Log.d(TAG, "task: $task has been inserted!")
//        }.toObservable().flatMap {
//            realm.where(Task::class.java)
//                    .findAllAsync()
//                    .asObservable()
//        }.subscribe({
//            Log.e(TAG, "Task count: ${it.count()}")
//        }, {
//            Log.e(TAG, "transaction error: ", it)
//        }, {
//            realm.rxExecuteTransaction { realm.deleteAll() }.subscribe()
//        })
    }

    override fun onDestroy() {
        TaskDataSource.close()
        super.onDestroy()
    }

    fun Realm.rxExecuteTransaction(transaction: (Realm) -> Unit): Single<Unit> = Single.create<Unit> { subscriber ->
        executeTransactionAsync(transaction, { subscriber.onSuccess(null) }, { subscriber.onError(it) })
    }
}
