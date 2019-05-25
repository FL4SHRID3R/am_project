package com.pwr.amproject.model

import com.pwr.amproject.logic.Judge
import com.pwr.amproject.utils.sortCardsByValue
import org.junit.Test

class LogicTests {

    private var deck = Deck()

    /**
     * Testing player winnings.
     */
    @Test
    fun testBestPlayers() {
        val judge = Judge()
        val table: MutableList<Card> = ArrayList()

        for (i in 1..deck.getCurrentDeckSize()) {
            if (i == 1 || i == 15 || i == 26 || i == 35 || i == 51)
                table.add(deck.draw())
            else
                deck.draw()
        }

        println("On table:")
        table.forEach { println("$it") }

        val hands: MutableMap<Int, Hand> = getHands(listOf(14, 16, 27, 18, 39, 11))

        println("\nIn hands:")
        hands.forEach { println("Player ${it.key} with hand ${it.value}") }

        println("\nWon: ${judge.findBestPlayers(hands.toMap(), table)}")

    }

    /**
     * Testing list<card> sorting
     */
    @Test
    fun testSorting() {
        deck.shuffle()
        val testTable: MutableList<Card> = ArrayList()
        for (i in 1..10) {
            testTable.add(deck.draw())
        }

        val test = testTable.toList()
        test.map { println(it) }
        sortCardsByValue(test)
        println("-------------------------------------------------------------")
        test.map { println(it) }
    }

    /**
     * Testing for One pair, Two pairs and Three of a kind.
     */
    @Test
    fun testJudge() {
        val judge = Judge()
        val table: MutableList<Card> = ArrayList()

        for (i in 1..deck.getCurrentDeckSize()) {
            if (i == 1 || i == 15 || i == 26 || i == 35 || i == 51)
                table.add(deck.draw())
            else
                deck.draw()
        }

        println("On table:")
        table.forEach { println("$it") }

        val hands: MutableMap<Int, Hand> = getHands(listOf(4, 14, 2, 14, 14, 27))

        println("\nIn hands:")
        hands.forEach { println("Player ${it.key} with hand ${it.value}") }

        println("\nWon: ${judge.findBestHand(hands.toMap(), table)}")
    }

    /**
     * Testing for Straight, Flush, Full House and Four of a kind
     */
    @Test
    fun testJudge2() {
        val judge = Judge()
        val table: MutableList<Card> = ArrayList()

        for (i in 1..deck.getCurrentDeckSize()) {
            if (i == 1 || i == 15 || i == 26 || i == 29 || i == 14)
                table.add(deck.draw())
            else
                deck.draw()
        }

        println("On table:")
        table.forEach { println("$it") }

        val hands: MutableMap<Int, Hand> = getHands(listOf(4, 5, 17, 22, 2, 27, 27, 40))

        println("\nIn hands:")
        hands.forEach { println("Player ${it.key} with hand ${it.value}") }

        println("\nWon: ${judge.findBestHand(hands.toMap(), table)}")
    }

    /**
     * Testing for Straight Flush and Royal Flush
     */
    @Test
    fun testJudge3() {
        val judge = Judge()
        val table: MutableList<Card> = ArrayList()

        for (i in 1..deck.getCurrentDeckSize()) {
            if (i == 1 || i == 2 || i == 22 || i == 23 || i == 24)
                table.add(deck.draw())
            else
                deck.draw()
        }

        println("On table:")
        table.forEach { println("$it") }

        val hands: MutableMap<Int, Hand> = getHands(listOf(20, 21, 25, 26))

        println("\nIn hands:")
        hands.forEach { println("Player ${it.key} with hand ${it.value}") }

        println("\nWon: ${judge.findBestHand(hands.toMap(), table)}")
    }

    private fun getHands(cards: List<Int>): MutableMap<Int, Hand> {
        val hands: MutableMap<Int, Hand> = HashMap()
        var player = 0
        for (i in 0 until (cards.size - 1) step 2) {
            hands[player++] = getHand(cards[i], cards[i + 1])
        }
        return hands
    }

    private fun getHand(i1: Int, i2: Int): Hand {
        var card: Card? = null
        var card2: Card? = null
        deck = Deck()
        var it = 0
        for (i in 1..deck.getCurrentDeckSize()) {
            if (i == i1 || i == i2) {
                if (it == 0) {
                    card = deck.draw()
                } else if (it == 1) {
                    card2 = deck.draw()
                    break
                }
                it++
            } else {
                deck.draw()
            }
        }
        return Hand(card!!, card2!!)
    }
}
