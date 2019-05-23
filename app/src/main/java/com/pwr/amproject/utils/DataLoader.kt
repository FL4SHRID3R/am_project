package com.pwr.amproject.utils

import com.pwr.amproject.R
import com.pwr.amproject.model.Card
import com.pwr.amproject.model.CardColour
import com.pwr.amproject.model.CardValue

fun createStandardDeck(): ArrayList<Card> {
    val array = ArrayList<Card>()
    val reverse = R.drawable.r_blue_back
    addClubs(array, reverse)
    addDiamonds(array, reverse)
    addHearts(array,reverse)
    addSpades(array,reverse)

    return array
}

private fun addDiamonds(array: ArrayList<Card>, reverse: Int) {
    val colour = CardColour.DIAMONDS
    array.add(Card(R.drawable.d2, reverse, colour, CardValue.TWO))
    array.add(Card(R.drawable.d3, reverse, colour, CardValue.THREE))
    array.add(Card(R.drawable.d4, reverse, colour, CardValue.FOUR))
    array.add(Card(R.drawable.d5, reverse, colour, CardValue.FIVE))
    array.add(Card(R.drawable.d6, reverse, colour, CardValue.SIX))
    array.add(Card(R.drawable.d7, reverse, colour, CardValue.SEVEN))
    array.add(Card(R.drawable.d8, reverse, colour, CardValue.EIGHT))
    array.add(Card(R.drawable.d9, reverse, colour, CardValue.NINE))
    array.add(Card(R.drawable.d10, reverse, colour, CardValue.TEN))
    array.add(Card(R.drawable.dj, reverse, colour, CardValue.JACK))
    array.add(Card(R.drawable.dq, reverse, colour, CardValue.QUEEN))
    array.add(Card(R.drawable.dk, reverse, colour, CardValue.KING))
    array.add(Card(R.drawable.da, reverse, colour, CardValue.ACE))
}
private fun addHearts(array: ArrayList<Card>, reverse: Int) {
    val colour = CardColour.HEARTS
    array.add(Card(R.drawable.h2, reverse, colour, CardValue.TWO))
    array.add(Card(R.drawable.h3, reverse, colour, CardValue.THREE))
    array.add(Card(R.drawable.h4, reverse, colour, CardValue.FOUR))
    array.add(Card(R.drawable.h5, reverse, colour, CardValue.FIVE))
    array.add(Card(R.drawable.h6, reverse, colour, CardValue.SIX))
    array.add(Card(R.drawable.h7, reverse, colour, CardValue.SEVEN))
    array.add(Card(R.drawable.h8, reverse, colour, CardValue.EIGHT))
    array.add(Card(R.drawable.h9, reverse, colour, CardValue.NINE))
    array.add(Card(R.drawable.h10, reverse, colour, CardValue.TEN))
    array.add(Card(R.drawable.hj, reverse, colour, CardValue.JACK))
    array.add(Card(R.drawable.hq, reverse, colour, CardValue.QUEEN))
    array.add(Card(R.drawable.hk, reverse, colour, CardValue.KING))
    array.add(Card(R.drawable.ha, reverse, colour, CardValue.ACE))
}
private fun addSpades(array: ArrayList<Card>, reverse: Int) {
    val colour = CardColour.SPADES
    array.add(Card(R.drawable.s2, reverse, colour, CardValue.TWO))
    array.add(Card(R.drawable.s3, reverse, colour, CardValue.THREE))
    array.add(Card(R.drawable.s4, reverse, colour, CardValue.FOUR))
    array.add(Card(R.drawable.s5, reverse, colour, CardValue.FIVE))
    array.add(Card(R.drawable.s6, reverse, colour, CardValue.SIX))
    array.add(Card(R.drawable.s7, reverse, colour, CardValue.SEVEN))
    array.add(Card(R.drawable.s8, reverse, colour, CardValue.EIGHT))
    array.add(Card(R.drawable.s9, reverse, colour, CardValue.NINE))
    array.add(Card(R.drawable.s10, reverse, colour, CardValue.TEN))
    array.add(Card(R.drawable.sj, reverse, colour, CardValue.JACK))
    array.add(Card(R.drawable.sq, reverse, colour, CardValue.QUEEN))
    array.add(Card(R.drawable.sk, reverse, colour, CardValue.KING))
    array.add(Card(R.drawable.sa, reverse, colour, CardValue.ACE))
}
private fun addClubs(array: ArrayList<Card>, reverse: Int) {
    val colour = CardColour.CLUBS
    array.add(Card(R.drawable.c2, reverse, colour, CardValue.TWO))
    array.add(Card(R.drawable.c3, reverse, colour, CardValue.THREE))
    array.add(Card(R.drawable.c4, reverse, colour, CardValue.FOUR))
    array.add(Card(R.drawable.c5, reverse, colour, CardValue.FIVE))
    array.add(Card(R.drawable.c6, reverse, colour, CardValue.SIX))
    array.add(Card(R.drawable.c7, reverse, colour, CardValue.SEVEN))
    array.add(Card(R.drawable.c8, reverse, colour, CardValue.EIGHT))
    array.add(Card(R.drawable.c9, reverse, colour, CardValue.NINE))
    array.add(Card(R.drawable.c10, reverse, colour, CardValue.TEN))
    array.add(Card(R.drawable.cj, reverse, colour, CardValue.JACK))
    array.add(Card(R.drawable.cq, reverse, colour, CardValue.QUEEN))
    array.add(Card(R.drawable.ck, reverse, colour, CardValue.KING))
    array.add(Card(R.drawable.ca, reverse, colour, CardValue.ACE))
}
