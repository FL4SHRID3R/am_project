package com.pwr.amproject.model

import com.pwr.amproject.utils.createStandardDeck

class Deck {
    private var cards: MutableList<Card> = createStandardDeck()
    private var graveyard: MutableList<Card> = ArrayList()

    fun get(index: Int): Card {
        val card = cards.removeAt(index)
        graveyard.add(card)
        return card
    }

    fun shuffle() {
        shuffle(cards.size)
    }

    fun shuffle(times: Int) {
        cards.addAll(graveyard)
        graveyard.clear()
        for (i in 1 until times) {
            cards.shuffle()
        }
    }

}
