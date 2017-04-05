package com.shrimpcolo.www.todokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shrimpcolo.www.todokotlin.data.Task
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import rx.Single
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
    private val realm: Realm by lazy { Realm.getDefaultInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.text = "Hello Kotlin 11"
        hello.textSize = 20.0f

//        val task = Task("title", "desc")
        Log.d(TAG, "Add Task! Realm path: " + realm.path)
        addTasks(Task("title", "desc"))

    }

    private fun addTasks(task: Task) {

        realm.rxExecuteTransaction {
            it.insert(task)
            Log.d(TAG, "task: $task has been inserted!")
        }.toObservable().flatMap {
            realm.where(Task::class.java)
                    .findAllAsync()
                    .asObservable()
        }.subscribe({
            Log.e(TAG, "Task count: ${it.count()}")
        }, {
            Log.e(TAG, "transaction error: ", it)
        }, {
            realm.rxExecuteTransaction { realm.deleteAll() }.subscribe()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun Realm.rxExecuteTransaction(transaction: (Realm) -> Unit): Single<Unit> = Single.create<Unit> { subscriber ->
        executeTransactionAsync(transaction, { subscriber.onSuccess(null) }, { subscriber.onError(it) })
    }
}
