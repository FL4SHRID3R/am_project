package com.pwr.amproject.model

class HandRankings {

    fun compareRanking(playerHand: Hand, dealedCards: List<Card>) : Hands {
        val toCompare: List<Card> = ArrayList(dealedCards)
        sort(toCompare)

        return when {
            isRoyalFlush(toCompare) -> Hands.ROYAL_FLUSH
            isStraightFlush(toCompare) -> Hands.STRAIGHT_FLUSH
            isFourOfAKind(toCompare) -> Hands.FOUR_OF_A_KIND
            isFullHouse(toCompare) -> Hands.FULL_HOUSE
            isFlush(toCompare) -> Hands.FLUSH
            isStraight(toCompare) -> Hands.STRAIGHT
            isThreeOfAKind(toCompare) -> Hands.THREE_OF_A_KIND
            isTwoPair(toCompare) -> Hands.TWO_PAIR
            isOnePair(toCompare) -> Hands.ONE_PAIR
            else -> Hands.HIGH_CARD
        }
    }

    private fun sort(cards: List<Card>) {
        cards.sortedWith(Comparator { card1, card2 ->
            when {
                card1.value.ordinal > card2.value.ordinal && card1.colour.ordinal > card2.colour.ordinal -> -1
                card1 == card2 -> 0
                else -> 1
            }
        })
    }

    private fun isRoyalFlush(cards: List<Card>): Boolean {
        return isStraightFlush(cards) && cards.last().value == CardValue.TEN
    }

    private fun isStraightFlush(cards: List<Card>): Boolean {
        return (isStraight(cards) && isFlush(cards))
    }

    private fun isFourOfAKind(cards: List<Card>): Boolean {
        return getFourOfAKind(cards) != null
    }

    private fun getFourOfAKind(cards: List<Card>): List<Card>? {
        for (i in 0 until cards.size - 4) {
            if(cards[i] == cards[i+1] && cards[i+1] == cards[i+2] && cards[i+2] == cards[i+3])
                return cards.subList(i, i+4)
        }
        return null
    }

    private fun isFullHouse(cards: List<Card>): Boolean {
        return (getThreeOfAKind(cards) != null && getOnePair(cards.reversed()) != null)
    }

    private fun isFlush(cards: List<Card>): Boolean {
        for (i in 0 until cards.size - 2) {
            if(cards[i].colour.ordinal != cards[i+1].colour.ordinal)
                return false
        }
        return true
    }

    private fun isStraight(cards: List<Card>): Boolean {
        for (i in 0 until cards.size - 2) {
            if(cards[i].value.ordinal - cards[i+1].value.ordinal != 1)
                return false
        }
        return true
    }

    private fun isThreeOfAKind(cards: List<Card>): Boolean {
        return getThreeOfAKind(cards) != null
    }

    private fun getThreeOfAKind(cards: List<Card>): Triple<Card, Card, Card>? {
        for (i in 0 until cards.size - 3) {
            if(cards[i] == cards[i+1] && cards[i+1] == cards[i+2])
                return Triple(cards[i], cards[i+1], cards[i+2])
        }
        return null
    }

    private fun isTwoPair(cards: List<Card>): Boolean {
        return getTwoPair(cards) != null
    }

    private fun getTwoPair(cards: List<Card>): Pair<Pair<Card, Card>, Pair<Card, Card>>? {
        val firstPair = getOnePair(cards)
        if(firstPair != null) {
            val secondPair = getOnePair(cards.reversed())
            if(secondPair != null) {
                return Pair(firstPair, secondPair)
            }
        }
        return null
    }

    private fun isOnePair(cards: List<Card>): Boolean {
        return getOnePair(cards) != null
    }

    private fun getOnePair(cards: List<Card>): Pair<Card, Card>? {
        for (i in 0 until cards.size - 2) {
            if(cards[i] == cards[i+1])
                return Pair(cards[i], cards[i+1])
        }
        return null
    }
}
