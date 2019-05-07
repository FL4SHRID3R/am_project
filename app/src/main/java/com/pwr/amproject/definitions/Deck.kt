package com.pwr.amproject.definitions

import com.pwr.amproject.utils.createStandardDeck

class Deck {
    private val cards : List<Card>

    constructor() {
        this.cards = createStandardDeck()
    }


}
