package com.pwr.amproject.BotLogic

import kotlin.random.Random

class Deck {
    private val deck = IntArray(52)

    private var hand1 = IntArray(2)
    private var hand2 = IntArray(2)
    private var hand3 = IntArray(2)
    private var hand4 = IntArray(2)

    private var table = IntArray(5)

    init {
        for (i in 0..51) {
            deck[i] = i
        }
    }

    //Tasuje talie w kolejnosci losowej

    private fun shuffle() {
        for (i in 0..51) {
            val index = i + Random.nextInt(0, 52 - i)

            val temp = deck[i]
            deck[i] = deck[index]
            deck[index] = temp
        }
    }

    //Pojedyńczy String zawierający:
    //-W zależności od ilości graczy (2-4) ręce graczy (od 4 do 8 kart, po kolei ręka gracza 1, reka gracza 2 itd)
    //-Stół ( 5 kart)
    //-Kolejność wygranych graczy (od najsilniejszej do najsłabszej ręki)
    //= oznacza remis pomiędzy dwoma graczami

    fun getCards(amountOfPlayers: Int): String {
        val j = Judge()
        shuffle()

        when (amountOfPlayers) {
            2 -> {
                hand1[0] = deck[1]
                hand1[1] = deck[3]

                hand2[0] = deck[2]
                hand2[1] = deck[4]

                table[0] = deck[6]
                table[1] = deck[7]
                table[2] = deck[8]
                table[3] = deck[10]
                table[4] = deck[12]

                var p1Score = j.translate(j.judge(hand1, table))
                var p2Score = j.translate(j.judge(hand2, table))

                //System.out.println(Arrays.toString(p1Score));
                //System.out.println(Arrays.toString(p2Score));

                return deck[1].toString() + " " + deck[3] + " " + deck[2] + " " + deck[4] + " " + deck[6] + " " + deck[7] + " " + deck[8] + " " + deck[10] + " " + deck[12] + " " + j.twoPeopleVictoryOrder(
                    p1Score,
                    p2Score
                )
            }
            3 -> {
                hand1 = IntArray(2)
                hand1[0] = deck[1]
                hand1[1] = deck[4]

                hand2 = IntArray(2)
                hand2[0] = deck[2]
                hand2[1] = deck[5]

                hand3 = IntArray(2)
                hand3[0] = deck[3]
                hand3[1] = deck[6]

                table = IntArray(5)
                table[0] = deck[8]
                table[1] = deck[9]
                table[2] = deck[10]
                table[3] = deck[12]
                table[4] = deck[14]

                var p1Score = j.translate(j.judge(hand1, table))
                var p2Score = j.translate(j.judge(hand2, table))
                var p3Score = j.translate(j.judge(hand3, table))

                return deck[1].toString() + " " + deck[4] + " " + deck[2] + " " + deck[5] + " " + deck[3] + " " + deck[6] + " " + deck[8] + " " + deck[9] + " " + deck[10] + " " + deck[12] + " " + deck[14]
            }
            else -> {
                hand1 = IntArray(2)
                hand1[0] = deck[1]
                hand1[1] = deck[5]

                hand2 = IntArray(2)
                hand2[0] = deck[2]
                hand2[1] = deck[6]

                hand3 = IntArray(2)
                hand3[0] = deck[3]
                hand3[1] = deck[7]

                hand4 = IntArray(2)
                hand4[0] = deck[4]
                hand4[1] = deck[8]

                table = IntArray(5)
                table[0] = deck[10]
                table[1] = deck[11]
                table[2] = deck[12]
                table[3] = deck[14]
                table[4] = deck[16]

                var p1Score = j.translate(j.judge(hand1, table))
                var p2Score = j.translate(j.judge(hand2, table))
                var p3Score = j.translate(j.judge(hand3, table))
                var p4Score = j.translate(j.judge(hand4, table))

                return deck[1].toString() + " " + deck[5] + " " + deck[2] + " " + deck[6] + " " + deck[3] + " " + deck[7] + " " + deck[4] + " " + deck[8] + " " + deck[10] + " " + deck[11] + " " + deck[12] + " " + deck[14] + " " + deck[16]
            }
        }
    }
}