package com.pwr.amproject.logic

import com.pwr.amproject.BotLogic.Deck

class Coordinator(
    private val bigBlind: Int,
    private val smallBlind: Int,
    private val maxPlayers: Int
) {
    private var deck = Deck()
    private var hands: MutableList<Pair<String, String>> = ArrayList()
    private lateinit var table: List<String>

    /**
     * (currentRound, currentPhase)
     * (0, 0) - start nowej rundy - rozdanie graczom świeżych kart
     * (0, 1) - faza pre-licytacji przed pokazaniem kart na stole
     * (1, 0) - wydanie pierwszych 3 kart na stół
     * (1, 1) - licytacja
     * (2, 0) - 4-ta karta
     * (2, 1) - licytacja
     * (3, 0) - 5-ta karta
     * (3, 1) - licytacja
     * (4, 0) - koniec rundy
     */

    private var currentRound: Int = 0
    private var currentPhase: Int = 0

    private var winners: String = ""

    private var currentPlayerId: Int = 0
    private var dealerId: Int = 0
    private var smallBlindId: Int = 1
    private var bigBlindId: Int = 2

    private var biddingPool: Int = 0


    fun roundSwitcher() {
        when (currentRound) {
            0 -> {
                when (currentPhase) {
                    0 -> {
                        init()
                    }
                    1 -> {
                        bid()
                    }
                }
            }
            1 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(3)
                    }
                    1 -> {
                        bid()
                    }
                }
            }
            2 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(1)
                    }
                    1 -> {
                        bid()
                    }
                }
            }
            3 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(1)
                    }
                    1 -> {
                        bid()
                    }
                }
            }
            4 -> {
                finish()
                nextRound()
            }
        }
    }

    private fun showCards(numberOfCards: Int) {

    }

    private fun nextRound() {

    }

    private fun bid() {
        currentPlayerId++
        if (allPlayersMoved()) {
            currentRound++
        }
    }

    private fun clear() {
        deck = Deck()
        hands = ArrayList()
        table = ArrayList()
        dealerId = 0
        smallBlindId = 1
        bigBlindId = 2
        currentRound = 0
        currentPlayerId = 0
        biddingPool = 0
    }

    private fun nextPhase() {
        currentPlayerId++
    }

    private fun nextTurn() {
        dealerId++
        smallBlindId++
        bigBlindId++

        biddingPool = 0
        currentPlayerId = dealerId
        winners = ""
        currentRound = 0
    }

    fun init() {
        clear()
        val allCards = deck.getCards(maxPlayers).split(" ")
        hands.add(Pair(allCards[0], allCards[1]))
        hands.add(Pair(allCards[2], allCards[3]))
        table = allCards.subList(4, 9)
        winners = allCards.subList(10, allCards.size).toString()

        if (maxPlayers == 3) {
            hands.add(Pair(allCards[4], allCards[5]))
            table = allCards.subList(6, 11)
            winners = allCards.subList(12, allCards.size).toString()
        }
        if (maxPlayers == 4) {
            hands.add(Pair(allCards[6], allCards[7]))
            table = allCards.subList(8, 13)
            winners = allCards.subList(14, allCards.size).toString()
        }


    }

    private fun finish() {
        println("Won player ")
    }

    private fun allPlayersMoved(): Boolean {
        return currentPlayerId == maxPlayers
    }
}
