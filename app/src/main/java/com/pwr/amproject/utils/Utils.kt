package com.pwr.amproject.utils

import com.pwr.amproject.model.Card

fun sortCardsByValue(cards: List<Card>): List<Card> {
    return cards.sortedWith(Comparator { card1, card2 ->
        when {
            card1.value.ordinal > card2.value.ordinal -> -1
            card1.value == card2.value -> 0
            else -> 1
        }
    })
}

fun sortCardsByColour(cards: List<Card>): List<Card> {
    return cards.sortedWith(Comparator { card1, card2 ->
        when {
            card1.colour.ordinal > card2.colour.ordinal -> -1
            card1.colour == card2.colour -> 0
            else -> 1
        }
    })
}
