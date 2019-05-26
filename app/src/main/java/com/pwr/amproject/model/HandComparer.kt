package com.pwr.amproject.model

import com.pwr.amproject.utils.getCardsList
import com.pwr.amproject.utils.sortCardsByColour
import com.pwr.amproject.utils.sortCardsByValue

class HandComparer {

    fun getCardsFromRank(rank: HandRanks, playerHand: Hand, dealtCards: List<Card>): List<Card> {
        val cards = getCardsList(playerHand, dealtCards)

        return when (rank) {
            HandRanks.ROYAL_FLUSH -> getRoyalFlush(cards)!!
            HandRanks.STRAIGHT_FLUSH -> getStraightFlush(cards)!!
            HandRanks.FOUR_OF_A_KIND -> getFourOfAKind(cards)!!
            HandRanks.FULL_HOUSE -> getFullHouse(cards)!!
            HandRanks.FLUSH -> getFlush(cards)!!
            HandRanks.STRAIGHT -> getStraight(cards)!!
            HandRanks.THREE_OF_A_KIND -> getThreeOfAKind(cards)!!
            HandRanks.TWO_PAIR -> getTwoPair(cards)!!
            HandRanks.ONE_PAIR -> getOnePair(cards)!!
            HandRanks.HIGH_CARD -> return listOf(cards[0])
        }
    }

    fun getHandRank(playerHand: Hand, dealtCards: List<Card>): HandRanks {
        val toCompare: List<Card> = getCardsList(playerHand, dealtCards)

        return when {
            toCompare.size < 5 -> HandRanks.HIGH_CARD
            isRoyalFlush(toCompare) -> HandRanks.ROYAL_FLUSH
            isStraightFlush(toCompare) -> HandRanks.STRAIGHT_FLUSH
            isFourOfAKind(toCompare) -> HandRanks.FOUR_OF_A_KIND
            isFullHouse(toCompare) -> HandRanks.FULL_HOUSE
            isFlush(toCompare) -> HandRanks.FLUSH
            isStraight(toCompare) -> HandRanks.STRAIGHT
            isThreeOfAKind(toCompare) -> HandRanks.THREE_OF_A_KIND
            isTwoPair(toCompare) -> HandRanks.TWO_PAIR
            isOnePair(toCompare) -> HandRanks.ONE_PAIR
            else -> HandRanks.HIGH_CARD
        }
    }



    private fun isRoyalFlush(cards: List<Card>): Boolean {
        return getRoyalFlush(cards) != null
    }

    private fun getRoyalFlush(cards: List<Card>): List<Card>? {
        val straightFlush = getStraightFlush(cards)
        return if (straightFlush != null && straightFlush.last().value == CardValue.TEN) {
            straightFlush
        } else {
            null
        }
    }

    private fun isStraightFlush(cards: List<Card>): Boolean {
        return getStraightFlush(cards) != null
    }

    private fun getStraightFlush(cards: List<Card>): List<Card>? {
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

    private fun getFourOfAKind(cards: List<Card>): List<Card>? {
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

    private fun getFullHouse(cards: List<Card>): List<Card>? {
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

    private fun getFlush(cards: List<Card>): List<Card>? {
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

    private fun getStraight(cards: List<Card>): List<Card>? {
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

    private fun getThreeOfAKind(cards: List<Card>): List<Card>? {
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

    private fun getTwoPair(cards: List<Card>): List<Card>? {
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

    private fun getOnePair(cards: List<Card>): List<Card>? {
        for (i in 0 until cards.size - 1) {
            if (cards[i].value == cards[i + 1].value)
                return listOf(cards[i], cards[i + 1])
        }
        return null
    }
}
