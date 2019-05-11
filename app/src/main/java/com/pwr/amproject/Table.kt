package com.pwr.amproject

import com.pwr.amproject.model.Deck
import com.pwr.amproject.model.Hand
import com.pwr.amproject.model.Player

class Table {
    private var players: MutableList<Player>
    private lateinit var hands: MutableList<Hand>

    private val smallBlind: Int
    private val bigBlind: Int
    private val deck: Deck
    private val betingRound: Int = 0

    private var dealerId: Int = 0

    constructor(players: MutableList<Player>, smallBlind: Int, bigBlind: Int, deck: Deck) {
        this.players = players
        this.smallBlind = smallBlind
        this.bigBlind = bigBlind
        this.deck = deck
    }

    fun clearTable() {
        hands = ArrayList(players.size)
    }

    fun dealNewHands() {
        hands.forEach { hand -> hand.firstCard = deck.draw() }
        hands.forEach { hand -> hand.secondCard = deck.draw() }
    }

    fun increaseIds() {
        dealerId = (dealerId + 1)%players.size
    }

    fun setPlayerHand(player: Player, hand: Hand) {
        players.add(player)
        hands.add(hand)
    }
}
