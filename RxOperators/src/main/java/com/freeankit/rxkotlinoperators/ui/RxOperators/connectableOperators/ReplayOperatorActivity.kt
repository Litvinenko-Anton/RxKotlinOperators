package com.freeankit.rxkotlinoperators.ui.RxOperators.connectableOperators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.freeankit.rxkotlinoperators.R
import com.freeankit.rxkotlinoperators.utils.Constant
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_example_operator.*

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 08/01/2018 (MM/DD/YYYY )
 */
class ReplayOperatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_operator)

        btn.setOnClickListener({ executeReplayOperator() })
    }


    /* Using replay operator, replay ensure that all observers see the same sequence
     * of emitted items, even if they subscribe after the Observable has begun emitting items
     */
    private fun executeReplayOperator() {
        val source = PublishSubject.create<Int>()
        val connectableObservable = source.replay(3) // bufferSize = 3 to retain 3 values to replay
        connectableObservable.connect() // connecting the connectableObservable

        connectableObservable.subscribe(getFirstObserver())

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)
        source.onNext(4)
        source.onComplete()

        /*
         * it will emit 2, 3, 4 as (count = 3), retains the 3 values for replay
         */
        connectableObservable.subscribe(getSecondObserver())

    }

    private fun getFirstObserver(): Observer<Int> {
        return object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(Constant().TAG, " First onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                textView.append(" First onNext : value : " + value)
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " First onNext value : " + value)
            }

            override fun onError(e: Throwable) {
                textView.append(" First onError : " + e.message)
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " First onError : " + e.message)
            }

            override fun onComplete() {
                textView.append(" First onComplete")
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " First onComplete")
            }
        }
    }

    private fun getSecondObserver(): Observer<Int> {
        return object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed)
                Log.d(Constant().TAG, " Second onSubscribe : " + d.isDisposed)
                textView.append(Constant().LINE_SEPARATOR)
            }

            override fun onNext(value: Int) {
                textView.append(" Second onNext : value : " + value!!)
                textView.append(Constant().LINE_SEPARATOR)
                Log.d(Constant().TAG, " Second onNext value : " + value)
            }

            override fun onError(e: Throwable) {
                textView.append(" Second onError : " + e.message)
                Log.d(Constant().TAG, " Second onError : " + e.message)
            }

            override fun onComplete() {
                textView.append(" Second onComplete")
                Log.d(Constant().TAG, " Second onComplete")
            }
        }
    }
}