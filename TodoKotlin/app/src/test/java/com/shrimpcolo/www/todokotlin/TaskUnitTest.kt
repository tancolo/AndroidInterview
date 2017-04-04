package com.shrimpcolo.www.todokotlin

import com.shrimpcolo.www.todokotlin.data.Task
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class TaskUnitTest {
    @Test
    fun testEquals() {
        val task1 = Task("title", "this is a task")

        val task2 = Task("title", "this is a task")
        assertNotEquals(task1, task2)

        val task3 = Task("title", "this is a task", "11111")
        val task4 = Task("title", "this is a task", "11111")

        assertEquals(task3, task4)
    }

    @Test
    fun testCompleted() {
        val task = Task("title", "this is a task")
        assertEquals(true, task.isActive())
        assertEquals(false, task.isCompleted)
    }

    @Test
    fun testTitleForList() {
        val task1 = Task("title", "this is a task")
        assertEquals("title", task1.getTitleForList())

        val desc = "this is a description"
        val task2 = Task("", desc)
        assertEquals(desc, task2.getTitleForList())
    }

    @Test
    fun testEmpty() {
        val task6 = Task("", "")
        assertEquals(true, task6.isEmpty())

        val task7 = Task("title", "")
        assertEquals(false, task7.isEmpty())

        val task8 = Task("", "this is a desc")
        assertEquals(false, task8.isEmpty())
    }

}