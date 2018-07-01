package com.freeankit.rxkotlinoperators.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.freeankit.rxkotlinoperators.R
import com.freeankit.rxkotlinoperators.ui.RxOperators.*
import com.freeankit.rxkotlinoperators.ui.RxOperators.combiningOperators.MergeOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.combiningOperators.ZipOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.connectableOperators.PublishSubjectOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.connectableOperators.ReplayOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.connectableOperators.ReplaySubjectOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.creatingOperators.DeferOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.creatingOperators.IntervalOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.errorHandlingOperators.RetryOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.filteringOperators.*
import com.freeankit.rxkotlinoperators.ui.RxOperators.mathematicalOperators.ConcatOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.mathematicalOperators.ReduceOperatorActivity
import com.freeankit.rxkotlinoperators.ui.RxOperators.transformingOperators.*
import com.freeankit.rxkotlinoperators.ui.RxOperators.utilityOperators.DelayOperatorActivity

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 08/12/2017 (MM/DD/YYYY )
 */
class OperatorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators)
    }

    fun startSimpleActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, SimpleOperatorActivity::class.java))
    }

    fun startMapActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, MapOperatorActivity::class.java))
    }

    fun startZipActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ZipOperatorActivity::class.java))
    }

    fun startDisposableActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, DisposableOperatorActivity::class.java))
    }

    fun startTakeActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, TakeOperatorActivity::class.java))
    }

    fun startTimerActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, TimerOperatorActivity::class.java))
    }

    fun startFlatMapActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, FlatMapOperatorActivity::class.java))
    }

    fun startIntervalActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, IntervalOperatorActivity::class.java))
    }

    fun startDebounceActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, DebounceOperatorActivity::class.java))
    }

    fun startSingleObserverActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, SingleObserverOperatorActivity::class.java))
    }

    fun startCompletableObserverActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, CompletableObserverOperatorActivity::class.java))
    }

    fun startFlowableActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, FlowableOperatorActivity::class.java))
    }

    fun startReduceActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ReduceOperatorActivity::class.java))
    }

    fun startBufferActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, BufferOperatorActivity::class.java))
    }

    fun startFilterActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, FilterOperatorActivity::class.java))
    }

    fun startSkipActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, SkipOperatorActivity::class.java))
    }

    fun startScanActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ScanOperatorActivity::class.java))
    }

    fun startReplayActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ReplayOperatorActivity::class.java))
    }

    fun startConcatActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ConcatOperatorActivity::class.java))
    }

    fun startMergeActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, MergeOperatorActivity::class.java))
    }

    fun startDeferActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, DeferOperatorActivity::class.java))
    }

    fun startDistinctActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, DistinctOperatorActivity::class.java))
    }

    fun startReplaySubjectActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ReplaySubjectOperatorActivity::class.java))
    }

    fun startPublishSubjectActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, PublishSubjectOperatorActivity::class.java))
    }

    fun startBehaviorSubjectActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, BehaviorSubjectActivity::class.java))
    }

    fun startAsyncSubjectActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, AsyncSubjectOperatorActivity::class.java))
    }

    fun startThrottleFirstActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ThrottleFirstOperatorActivity::class.java))
    }

    fun startThrottleLastActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, ThrottleLastOperatorActivity::class.java))
    }

    fun startWindowActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, WindowOperatorActivity::class.java))
    }

    fun startDelayActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, DelayOperatorActivity::class.java))
    }

    fun startRetryActivity(view: View) {
        startActivity(Intent(this@OperatorsActivity, RetryOperatorActivity::class.java))
    }
}