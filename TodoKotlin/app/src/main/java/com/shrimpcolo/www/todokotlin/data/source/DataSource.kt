package com.shrimpcolo.www.todokotlin.data.source

import com.shrimpcolo.www.todokotlin.data.Task
import rx.Observable


/**
 * Created by Johnny Tam on 2017/4/5.
 */

interface DataSource<T> {

    fun findAll(): Observable<List<T>>

    fun findOne(id: String): Observable<T>

    fun save(data: T)

    fun deleteAll()

    fun deleteOne(id: String)

//    fun completeTask(task: Task)
//
//    fun completeTask(taskId: String)
//
//    fun activateTask(task: Task)
//
//    fun activateTask(taskId: String)
//
//    fun clearCompletedTasks()
//
//    fun refreshTasks()
//

//

}