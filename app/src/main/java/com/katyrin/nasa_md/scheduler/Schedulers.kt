package com.katyrin.nasa_md.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface Schedulers {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun main(): Scheduler
}