package com.pwr.amproject.model

import java.io.Serializable

data class Hand (
    var firstCard: Card,
    var secondCard: Card
) : Serializable
