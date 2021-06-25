package com.katyrin.nasa_md.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface Schedulers {
    fun background(): Scheduler
    fun main(): Scheduler
}