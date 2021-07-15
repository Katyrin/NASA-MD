package com.katyrin.nasa_md.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

object DefaultSchedulers : Schedulers {

    override fun computation(): Scheduler = io.reactivex.rxjava3.schedulers.Schedulers.computation()

    override fun io(): Scheduler = io.reactivex.rxjava3.schedulers.Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}