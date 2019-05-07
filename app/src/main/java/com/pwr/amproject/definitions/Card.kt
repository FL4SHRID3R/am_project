package com.pwr.amproject.definitions

import java.io.Serializable

data class Card (
    val imageSource: Int,
    val reverseSource: Int,
    val colour: CardColour,
    val value: CardValue
) : Serializable


