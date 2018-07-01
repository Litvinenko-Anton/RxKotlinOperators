package com.freeankit.rxkotlinoperators.ui.RxOperators.combiningOperators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.freeankit.rxkotlinoperators.R
import com.freeankit.rxkotlinoperators.model.User
import com.freeankit.rxkotlinoperators.utils.Constant
import com.freeankit.rxkotlinoperators.utils.Utils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_example_operator.*

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 08/12/2017 (MM/DD/YYYY )
 */
class ZipOperatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_operator)

        btn.setOnClickListener({ executeZipOperator() })
    }

    /*
  * Here we are getting two user list
  * One, the list of Kotlin fans
  * Another one, the list of Java fans
  * Then we are finding the list of users who loves both
  */
    private fun executeZipOperator() {
        progress.visibility = View.VISIBLE
        Observable.zip<List<User>, List<User>, List<User>>(getKotlinFansObservable(), getJavaFansObservable(),
                BiFunction<List<User>, List<User>, List<User>> { kotlinFans, javaFans -> Utils().filterUserWhoLovesBoth(kotlinFans, javaFans) })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver())
    }

    private fun getKotlinFansObservable(): Observable<List<User>> {
        return Observable.create<List<User>> { e ->
            if (!e.isDisposed) {
                e.onNext(Utils().getUserListWhoLovesKotlin())
                e.onComplete()
            }
        }
    }

    private fun getJavaFansObservable(): Observable<List<User>> {
        return Observable.create<List<User>> { e ->
            if (!e.isDisposed) {
                e.onNext(Utils().getUserListWhoLovesJava())
                e.onComplete()
            }
        }
    }

    private fun getObserver(): Observer<List<User>> {
        return object : Observer<List<User>> {

            override fun onSubscribe(d: Disposable) {
                Log.d(Constant().TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(userList: List<User>) {
                textView.append(" onNext")
                textView.append(Constant().LINE_SEPARATOR)
                for (user in userList) {
                    textView.append(" loginName : " + user.login)
                    textView.append(Constant().LINE_SEPARATOR)
                }
                Log.d(Constant().TAG, " onNext : " + userList.size)
                progress.visibility = View.GONE
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " onError : " + e.message)
                progress.visibility = View.GONE
            }

            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " onComplete")
                progress.visibility = View.GONE
            }
        }
    }
}