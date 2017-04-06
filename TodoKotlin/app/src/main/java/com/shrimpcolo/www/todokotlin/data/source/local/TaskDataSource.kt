package com.shrimpcolo.www.todokotlin.data.source.local

import android.util.Log
import com.shrimpcolo.www.todokotlin.data.Task
import io.realm.Realm
import rx.Observable
import rx.lang.kotlin.toSingle

/**
 * Created by Johnny Tam on 2017/4/5.
 */
object TaskDataSource : LocalDataSource<Task>(Task::class.java)