package com.pwr.amproject.model

abstract class Player(money: Long) {
    var money: Long = money

    abstract fun fold()

    abstract fun check()

    abstract fun raise(amount: Long)

    abstract fun call()

    abstract fun allIn()
}
