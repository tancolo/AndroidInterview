package com.shrimpcolo.www.todokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shrimpcolo.www.todokotlin.data.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello.text = "Hello Kotlin 11"
        hello.textSize = 20.0f

        val task = Task("title1", "this is a description")

    }
}
