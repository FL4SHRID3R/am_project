package com.pwr.amproject.model

import java.io.Serializable

data class Hand (
    val firstCard: Card,
    val secondCard: Card
) : Serializable
