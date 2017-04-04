package com.shrimpcolo.www.todokotlin.data

import java.util.*

/**
 * Created by Johnny Tam on 2017/4/4.
 */
data class Task(val title: String, val description: String,
                val id: String = UUID.randomUUID().toString(),
                val isCompleted : Boolean = false) {

    fun getTitleForList(): String = if (title.isEmpty()) description else title

    fun isActive(): Boolean = !isCompleted

    fun isEmpty(): Boolean = title.isEmpty() && description.isEmpty()

    private fun <T: Task> equals(task: T): Boolean =
            id == task.id &&
            title == task.title &&
            description == task.description

    override fun equals(other: Any?): Boolean = other?.let { if (it is Task) equals(it) else false } ?: false
}