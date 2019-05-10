package com.pwr.amproject.model

import com.pwr.amproject.utils.createStandardDeck
import java.io.Serializable

class Deck: Serializable {
    private var cards: MutableList<Card> = createStandardDeck()
    private var graveyard: MutableList<Card> = ArrayList()

    fun draw(): Card {
        if (cards.isEmpty()) {
            shuffle()
        }
        val card = cards.removeAt(0)
        graveyard.add(card)
        return card
    }

    fun shuffle() {
        shuffle(cards.size)
    }

    fun shuffle(times: Int) {
        addGraveyardToDeck()
        for (i in 1 until times) {
            cards.shuffle()
        }
    }

    fun getCurrentDeckSize(): Int {
        return cards.size
    }

    private fun addGraveyardToDeck() {
        cards.addAll(graveyard)
        graveyard.clear()
    }

}
