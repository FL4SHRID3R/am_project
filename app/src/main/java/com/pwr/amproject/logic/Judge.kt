package com.pwr.amproject.logic

import com.pwr.amproject.model.Card
import com.pwr.amproject.model.Hand
import com.pwr.amproject.model.HandComparer
import com.pwr.amproject.model.HandRanks
import com.pwr.amproject.utils.getCardsList

class Judge {

    private val rank: HandComparer = HandComparer()

    /**
     * Returns strongest hand from given ones.
     * @param hands map containing hand for given playerID
     * @param dealtCards cards dealt by dealer on the table
     * @return ID of player with strongest hand
     */
    fun findBestHand(hands: Map<Int, Hand>, dealtCards: List<Card>): Pair<Int, HandRanks> {
        val playerHands: MutableMap<Int, HandRanks> = HashMap()
        hands.forEach { playerHands[it.key] = rank.getHandRank(it.value, dealtCards) }

        var highestRank: HandRanks = HandRanks.HIGH_CARD
        playerHands.forEach {
            if (it.value.ordinal < highestRank.ordinal) {
                highestRank = it.value
            }
        }
        playerHands.forEach { if (it.value == highestRank) return Pair(it.key, it.value) }

        val first = playerHands.keys.first()
        return Pair(first, playerHands[first]!!)
    }

    /**
     * Returns list of players with best hands.
     * Usually should return a list of a single player but during draws return all players
     * with strongest hands.
     * @param hands map containing hand for given playerID
     * @param dealtCards cards dealt by dealer on the table
     * @return List players ID with strongest hands
     */
    fun findBestPlayers(hands: Map<Int, Hand>, dealtCards: List<Card>): List<Int> {
        val playersWithBestRanks = getBestPlayersOnRanks(hands, dealtCards)
        val result: MutableList<Int> = ArrayList()
        if (playersWithBestRanks.size > 1) {
            val onCards = getBestPlayersOnCards(playersWithBestRanks, hands, dealtCards)
            onCards.forEach { result.add(it.first) }
        } else {
            playersWithBestRanks.forEach { result.add(it.first) }
        }
        return result.toList()
    }

    /**
     * Finds best players basing purely on highest cards.
     * For example if two players have a pair it will pick the one with either
     * higher pair or with higher runner up.
     */
    private fun getBestPlayersOnCards(
        playersWithBestRanks: List<Pair<Int, HandRanks>>,
        hands: Map<Int, Hand>,
        dealtCards: List<Card>
    ): List<Pair<Int, HandRanks>> {
        val firstBest = playersWithBestRanks.first()
        var bestPlayers = mutableListOf(firstBest)
        var bestHand = rank.getCardsFromRank(firstBest.second, hands.getValue(firstBest.first), dealtCards)

        // Get highest rank on hands
        for (i in 1 until playersWithBestRanks.size) {
            val player = playersWithBestRanks[i]
            val tempHand = rank.getCardsFromRank(player.second, hands.getValue(player.first), dealtCards)
            if (bestHand[0].value == tempHand[0].value) {
                bestPlayers.add(player)
            } else if (bestHand[0].value.ordinal < tempHand[0].value.ordinal) {
                bestPlayers = mutableListOf(player)
                bestHand = tempHand
            }
        }

        // If there is still more than one player look for runner ups
        var finalBestPlayers: MutableList<Pair<Int, HandRanks>> = ArrayList()
        if (bestPlayers.size > 1) {
            var lastBest = bestPlayers[0]
            finalBestPlayers.add(lastBest)
            for (i in 1 until bestPlayers.size) {
                val lastBestHand = getCardsList(hands.getValue(lastBest.first), dealtCards)
                val tempHand = getCardsList(hands.getValue(bestPlayers[i].first), dealtCards)
                var same = lastBestHand.size
                for (j in 0 until lastBestHand.size) {
                    if (lastBestHand[j].value.ordinal < tempHand[j].value.ordinal) {
                        lastBest = bestPlayers[i]
                        finalBestPlayers = mutableListOf(lastBest)
                    } else if (lastBestHand[j].value.ordinal == tempHand[j].value.ordinal){
                        same--
                    } else {
                        break
                    }
                }
                if(same == 0) {
                    finalBestPlayers.add(bestPlayers[i])
                }
            }
        } else {
            finalBestPlayers = bestPlayers
        }
        return finalBestPlayers.toList()
    }

    /**
     * Finds best players basing purely on hand ranking.
     * For example it does not differentiate one Two pair from another one.
     */
    private fun getBestPlayersOnRanks(
        hands: Map<Int, Hand>,
        dealtCards: List<Card>
    ): List<Pair<Int, HandRanks>> {

        var reducedHands = hands.toMutableMap()
        var bestPlayers: MutableList<Pair<Int, HandRanks>> = ArrayList()

        var tempBest = findBestHand(reducedHands, dealtCards)
        var lastBestPlayer = tempBest

        while (reducedHands.isNotEmpty()) {
            bestPlayers.add(lastBestPlayer)
            reducedHands.remove(lastBestPlayer.first)
            if (reducedHands.isEmpty()) {
                break
            }

            tempBest = findBestHand(reducedHands, dealtCards)
            if (lastBestPlayer.second.ordinal < tempBest.second.ordinal) {
                break
            }
            lastBestPlayer = tempBest
        }
        return bestPlayers.toList()
    }
}
