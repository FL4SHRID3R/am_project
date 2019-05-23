package com.pwr.amproject.model

import java.io.Serializable

abstract class Player(money: Long): Serializable {
    var money: Long = money

    abstract fun fold()

    abstract fun check()

    abstract fun raise(amount: Long)

    abstract fun call()

    abstract fun allIn()
}
