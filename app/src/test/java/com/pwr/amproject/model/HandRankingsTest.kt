package com.pwr.amproject.model

import com.pwr.amproject.utils.sortCards
import org.junit.Test

class HandRankingsTest {

    @Test
    fun test() {
        val deck = Deck()
        deck.shuffle()
        val testTable: MutableList<Card> = ArrayList()
        for (i in 1..30) {
            testTable.add(deck.draw())
        }

        val test = testTable.toList()
        test.map { println(it) }
        sortCards(test)
        println("-------------------------------------------------------------")
        test.map { println(it) }



    }
}
