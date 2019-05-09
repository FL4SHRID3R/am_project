package com.pwr.amproject

import com.pwr.amproject.model.Deck
import com.pwr.amproject.model.Hand
import com.pwr.amproject.model.Player
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BasicLogicUnitTests {
    @Test
    fun testShuffle() {
        val deck = Deck()
        var initDeck = deck.cards
        deck.shuffle()
        println(deck.cards)
        println()
        println(initDeck)
        assertNotEquals(deck.cards, initDeck)
    }

    @Test
    fun testGetters() {
        val deck = Deck()
        val hand = Hand(deck.get(0), deck.get(51))
        val player = TestPlayer()


    }

    private class TestPlayer: Player(1000) {
        override fun raise(amount: Long) {
        }

        override fun fold() {
        }

        override fun check() {
        }

        override fun call() {
        }

        override fun allIn() {
        }
    }
}
