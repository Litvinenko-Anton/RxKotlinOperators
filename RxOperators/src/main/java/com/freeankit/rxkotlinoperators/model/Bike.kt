package com.freeankit.rxkotlinoperators.model

import io.reactivex.Observable

/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 11/01/2018 (MM/DD/YYYY )
 */
class Bike {
    private var brand: String? = null

    fun setBrand(brand: String) {
        this.brand = brand
    }

    fun brandDeferObservable(): Observable<String> {
        return Observable.defer { Observable.just(brand!!) }
    }
}