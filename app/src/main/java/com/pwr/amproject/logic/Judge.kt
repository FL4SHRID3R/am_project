package com.pwr.amproject.logic

import com.pwr.amproject.model.Card
import com.pwr.amproject.model.Hand
import com.pwr.amproject.model.HandComparer
import com.pwr.amproject.model.HandRanks

class Judge {

    private val rank: HandComparer = HandComparer()

    fun findBestHand(hands: Map<Int, Hand>, dealtCards: List<Card>): Int {
        val playerHands: MutableMap<Int, HandRanks> = HashMap()
        val playerIdWithBestHand: Int
        hands.forEach { playerHands[it.key] = rank.getHandRank(it.value, dealtCards) }

        var highestRank: HandRanks = HandRanks.HIGH_CARD
        playerHands.forEach {
            if (it.value.ordinal < highestRank.ordinal) {
                highestRank = it.value
            }
        }
        playerHands.forEach { println("${it.key} ${it.value}") }
        playerHands.forEach { if (it.value == highestRank) return it.key }

        return playerHands.keys.first()
    }
}
