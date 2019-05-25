package com.pwr.amproject.model

import com.pwr.amproject.utils.sortCardsByColour
import com.pwr.amproject.utils.sortCardsByValue

class HandComparer {

    fun getHandRank(playerHand: Hand, dealtCards: List<Card>): HandRanks {
        val toSort: MutableList<Card> = dealtCards.toMutableList()
        toSort.add(playerHand.firstCard)
        toSort.add(playerHand.secondCard)

        val toCompare: List<Card> = sortCardsByValue(toSort)

        println(
            "=========================================================================\n" +
                    "By value: $toCompare"
        )
        println(
            "By color: ${sortCardsByColour(toCompare)}\n" +
                    "========================================================================="
        )

        return if (toCompare.size < 5) HandRanks.HIGH_CARD
        else if (isRoyalFlush(toCompare)) HandRanks.ROYAL_FLUSH
        else if (isStraightFlush(toCompare)) HandRanks.STRAIGHT_FLUSH
        else if (isFourOfAKind(toCompare)) HandRanks.FOUR_OF_A_KIND
        else if (isFullHouse(toCompare)) HandRanks.FULL_HOUSE
        else if (isFlush(toCompare)) HandRanks.FLUSH
        else if (isStraight(toCompare)) HandRanks.STRAIGHT
        else if (isThreeOfAKind(toCompare)) HandRanks.THREE_OF_A_KIND
        else if (isTwoPair(toCompare)) HandRanks.TWO_PAIR
        else if (isOnePair(toCompare)) HandRanks.ONE_PAIR
        else HandRanks.HIGH_CARD
    }

    private fun isRoyalFlush(cards: List<Card>): Boolean {
        return getRoyalFlush(cards) != null
    }

    fun getRoyalFlush(cards: List<Card>): List<Card>? {
        val straightFlush = getStraightFlush(cards)
        if(straightFlush != null && straightFlush.last().value == CardValue.TEN) {
            return straightFlush
        } else {
            return null
        }
    }

    private fun isStraightFlush(cards: List<Card>): Boolean {
        return getStraightFlush(cards) != null
    }

    fun getStraightFlush(cards: List<Card>): List<Card>? {
        val flush = getFlush(cards)
        if (flush != null) {
            val straight = getStraight(sortCardsByValue(flush))
            if (straight != null)
                return straight
        }
        return null
    }

    private fun isFourOfAKind(cards: List<Card>): Boolean {
        return getFourOfAKind(cards) != null
    }

    fun getFourOfAKind(cards: List<Card>): List<Card>? {
        for (i in 0 until cards.size - 3) {
            if (cards[i].value == cards[i + 1].value &&
                cards[i + 1].value == cards[i + 2].value &&
                cards[i + 2].value == cards[i + 3].value
            )
                return cards.subList(i, i + 4)
        }
        return null
    }

    private fun isFullHouse(cards: List<Card>): Boolean {
        return getFullHouse(cards) != null
    }

    fun getFullHouse(cards: List<Card>): List<Card>? {
        val threeOfAKind = getThreeOfAKind(cards)
        if (threeOfAKind != null) {
            val reduced = cards.toMutableList()
            reduced.removeAll(threeOfAKind)
            val pair = getOnePair(reduced)
            if (pair != null)
                return listOf(threeOfAKind, pair).flatten()
        }
        return null
    }

    private fun isFlush(cards: List<Card>): Boolean {
        return getFlush(cards) != null
    }

    fun getFlush(cards: List<Card>): List<Card>? {
        val newCards = sortCardsByColour(cards)
        var result: MutableList<Card> = mutableListOf(newCards[0])
        for (i in 0 until newCards.size - 1) {
            if (newCards[i].colour.ordinal == newCards[i + 1].colour.ordinal) {
                result.add(newCards[i + 1])
            } else {
                if (result.size < 5) {
                    result = mutableListOf(newCards[i + 1])
                }
            }
        }
        return if (result.size == 5)
            result.toList()
        else
            null
    }

    private fun isStraight(cards: List<Card>): Boolean {
        return getStraight(cards) != null
    }

    fun getStraight(cards: List<Card>): List<Card>? {
        var result: MutableList<Card> = mutableListOf(cards[0])
        for (i in 0 until cards.size - 1) {
            if (cards[i].value.ordinal - cards[i + 1].value.ordinal == 1) {
                result.add(cards[i + 1])
            } else {
                if (result.size < 5) {
                    result = mutableListOf(cards[i + 1])
                }
            }
        }
        return if (result.size == 5)
            result.toList()
        else
            null
    }

    private fun isThreeOfAKind(cards: List<Card>): Boolean {
        return getThreeOfAKind(cards) != null
    }

    fun getThreeOfAKind(cards: List<Card>): List<Card>? {
        for (i in 0 until cards.size - 2) {
            if (cards[i].value == cards[i + 1].value &&
                cards[i + 1].value == cards[i + 2].value
            )
                return listOf(cards[i], cards[i + 1], cards[i + 2])
        }
        return null
    }

    private fun isTwoPair(cards: List<Card>): Boolean {
        return getTwoPair(cards) != null
    }

    fun getTwoPair(cards: List<Card>): List<Card>? {
        val firstPair = getOnePair(cards)
        if (firstPair != null) {
            val reducedCards = cards.toMutableList()
            reducedCards.removeAll(firstPair)
            val secondPair = getOnePair(reducedCards)
            if (secondPair != null) {
                return listOf(firstPair, secondPair).flatten()
            }
        }
        return null
    }

    private fun isOnePair(cards: List<Card>): Boolean {
        return getOnePair(cards) != null
    }

    fun getOnePair(cards: List<Card>): List<Card>? {
        for (i in 0 until cards.size - 1) {
            if (cards[i].value == cards[i + 1].value)
                return listOf(cards[i], cards[i + 1])
        }
        return null
    }
}
