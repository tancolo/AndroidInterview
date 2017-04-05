package com.shrimpcolo.www.todokotlin.data

import com.shrimpcolo.www.todokotlin.Noarg
import com.shrimpcolo.www.todokotlin.Open
import io.realm.RealmObject
import java.util.*

/**
 * Created by Johnny Tam on 2017/4/4.
 */
@Noarg
@Open
data class Task(var title: String, var description: String,
                var id: String = UUID.randomUUID().toString(),
                var isCompleted : Boolean = false) : RealmObject() {

    fun getTitleForList(): String = if (title.isEmpty()) description else title

    fun isActive(): Boolean = !isCompleted

    fun isEmpty(): Boolean = title.isEmpty() && description.isEmpty()

    private fun <T: Task> equals(task: T): Boolean =
            id == task.id &&
            title == task.title &&
            description == task.description

    override fun equals(other: Any?): Boolean = other?.let { if (it is Task) equals(it) else false } ?: false
}