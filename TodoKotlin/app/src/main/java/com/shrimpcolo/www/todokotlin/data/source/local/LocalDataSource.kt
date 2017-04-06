package com.shrimpcolo.www.todokotlin.data.source.local

import android.util.Log
import com.shrimpcolo.www.todokotlin.data.Task
import com.shrimpcolo.www.todokotlin.data.source.DataSource
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmQuery
import rx.Observable
import rx.lang.kotlin.toSingle


/**
 * Created by Johnny Tam on 2017/4/5.
 */

abstract class LocalDataSource<T: RealmModel>(val clazz: Class<T>) : DataSource<T> {

    val realm: Realm by lazy { Realm.getDefaultInstance() }

    override fun findAll(): Observable<List<T>> = find()

    override fun findOne(id: String): Observable<T> = find(id).map { it.first() }

    private fun find(id: String = ""): Observable<List<T>> {
        var query = realm.where(clazz)

        if (!id.isEmpty()) {
            query = query.equalTo("id", id)
        }

        return query
                .findAllAsync()
                .asObservable()
                .filter {  !it.isEmpty() }
                .map { it.toList() }
                .distinctUntilChanged()
    }

    override fun save(data: T) {
        realm.executeTransactionAsync(
                { it.insert(data) },
                { Log.d("TANHQ", "insert succeed: $data") },
                { Log.e("TANHQ", "Error: ", it) })
    }

    override fun deleteAll() {
//        realm.where(clazz)
//                .findAllAsync()
//                .asObservable()
//                .filter { !it.isEmpty() }
//                .distinctUntilChanged()
//                .subscribe {
//                    realm.beginTransaction()
//                    it.deleteAllFromRealm()
//                    realm.commitTransaction()
//                }

        realm.executeTransactionAsync(
                Realm::deleteAll,
                { Log.d("TANHQ", "delete succeed!") },
                { Log.e("TANHQ", "Error: ", it) })
    }

    override fun deleteOne(id: String) {
       realm.where(clazz)
               .equalTo("id", id)
               .findAllAsync()
               .asObservable()
               .filter { !it.isEmpty() }
               .distinctUntilChanged()
               .subscribe {
                   realm.beginTransaction()
                   it.deleteAllFromRealm()
                   realm.commitTransaction()
               }

    }

    fun close() {
        if (!realm.isClosed) realm.close()
    }
}