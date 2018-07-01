package com.freeankit.rxkotlinoperators.ui.RxPagination

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.ReplayProcessor
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

interface PagingAdapter<T> {

    fun skipListExpired()

    fun skipList()

    fun updateList(newItems: List<T>)

}

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5
    // The current offset index of data you have loaded
    private var currentPage = 0
    // The total number of items in the data set after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var loading = true
    // Sets the starting page index
    private val startingPageIndex = 0
    private var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: RecyclerView.LayoutManager) {
        this.mLayoutManager = layoutManager
        if (layoutManager is GridLayoutManager)
            visibleThreshold *= (layoutManager as GridLayoutManager).spanCount
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions) // get maximum element within the list
            }
            is GridLayoutManager -> lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                load(totalItemCount, view)
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            load(totalItemCount, view)
        }
    }

    fun firstLoad() {
        if (mLayoutManager.itemCount == 0)
            load(0, null)
    }

    private fun load(totalItemCount: Int, view: RecyclerView?) {
        currentPage++
        onLoadMore(currentPage, totalItemCount, view)
        loading = true
    }

    // Call this method whenever performing new searches
    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

}

class RxPagination {

    private val compositeDisposable = CompositeDisposable()
    var paginator: ReplayProcessor<Int>
    var flowable: Flowable<MutableList<String>>


    constructor(view: RecyclerView?, dataFromNetwork: (Int) -> Publisher<MutableList<String>>, progressBarView: View?) {
        initScroll(view)
        paginator = ReplayProcessor.create()
        flowable = initPublishProcessor(dataFromNetwork, progressBarView)
    }

    class Builder {

        var view: RecyclerView? = null
        var progressBarView: View? = null
        lateinit var function: (Int) -> Publisher<MutableList<String>>

        fun dataFromNetwork(function: (page: Int) -> Publisher<MutableList<String>>) = apply { this.function = function }
        fun recyclerView(view: RecyclerView) = apply { this.view = view }
        fun progressBarView(progressBarView: View) = apply { this.progressBarView = progressBarView }

        fun paginate(startWith: Int): Flowable<MutableList<String>> {
            val rxPaginator = RxPagination(view, function, progressBarView)
            rxPaginator.paginator.onNext(startWith)
            return rxPaginator.flowable
        }
    }

    private fun initScroll(view: RecyclerView?) {
        view?.addOnScrollListener(object : EndlessRecyclerViewScrollListener(view.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                paginator.onNext(page)
            }
        })
    }

    private fun initPublishProcessor(dataFromNetwork: (Int) -> Publisher<MutableList<String>>,
                                     progressBarView: View?): Flowable<MutableList<String>> {
        return paginator.onBackpressureDrop()
                .concatMap { page ->
                    progressBarView?.visibility = View.VISIBLE
                    dataFromNetwork(page)
                }
                .doOnError { t ->
                    t.printStackTrace()
                    progressBarView?.visibility = View.GONE
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { progressBarView?.visibility = View.GONE }
    }

    private fun dataFromNetwork(page: Int): Publisher<ArrayList<String>>? {
        return Flowable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .map {
                    val items = ArrayList<String>()
                    for (i in 1..10) {
                        items.add("ListItem " + (page * 10 + i))
                    }
                    items
                }

    }

    private fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}