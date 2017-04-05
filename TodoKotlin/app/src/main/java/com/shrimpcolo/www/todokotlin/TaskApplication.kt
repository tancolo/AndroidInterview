package com.shrimpcolo.www.todokotlin

import android.app.Application
import io.realm.Realm

/**
 * Created by Johnny Tam on 2017/4/4.
 */

class TaskApplication : Application() {
    override fun onCreate() {
        Realm.init(this)
    }
}
