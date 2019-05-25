package com.pwr.amproject.utils

import com.pwr.amproject.model.Card

fun sortCards(cards: List<Card>): List<Card> {
    return cards.sortedWith(Comparator { card1, card2 ->
        when {
            card1.value.ordinal > card2.value.ordinal -> -1
            card1 == card2 -> 0
            else -> 1
        }
    })
}
