package com.pwr.amproject

import com.pwr.amproject.model.Deck
import com.pwr.amproject.model.Hand
import com.pwr.amproject.model.Player

class Table {
    private lateinit var players: MutableMap<Player, Hand>
    private val smallBlind: Int
    private val bigBlind: Int

    private val deck : Deck

    constructor(smallBlind: Int, bigBlind: Int, deck: Deck) {
        this.smallBlind = smallBlind
        this.bigBlind = bigBlind
        this.deck = deck
    }

    fun clearTable() {
        players = HashMap()
    }

    fun setPlayerHand(player: Player, hand: Hand) {
        players[player] = hand
    }
}
