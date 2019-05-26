package com.pwr.amproject.BotLogic

import kotlin.math.max

class Judge {
    private fun tieSolver(tScores: IntArray, places: IntArray): String{
        var t1 = tScores
        var t2 = places

        for(i in 0 until t1.size){
            for(j in 0 until t1.size - 1){
                if(t1[j] < t1[j + 1]){
                    var temp1 = t1[j+1]
                    var temp2 = t2[j+1]
                    t1[j+1] = t1[j]
                    t2[j+1] = t2[j]
                    t1[j] = temp1
                    t2[j] = temp2
                }
            }
        }

        var s = ""

        for(i in 0 until t1.size - 1){
            s += t2[i]
            if(t1[i] == t1[i+1]){
                s += "="
            }else{
                s+= " "
            }
        }

        return s + t2[t2.size - 1]
    }

    //kolejność kolorów: czerw(kier), dzwonek(karo), żołądź(trefl), wino(pik)

    private fun getCardColorID(id: Int): Int {
        if (id > 12) {
            if (id > 25) {
                if (id > 38) {
                    return 3
                } else {
                    return 2
                }
            } else {
                return 1
            }
        } else {
            return 0
        }
    }

    //kolejność figur: 2, 3, 4, 5, 6, 7, 8, 9, 10, walet, dama, król, as

    private fun getCardFigureID(id: Int): Int {
        return id % 13
    }

    private fun hasDubs(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] == c[1] || c[1] == c[2] || c[2] == c[3] || c[3] == c[4]) {
            return true
        }

        return false
    }

    private fun hasDoubleDubs(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] == c[1]) {
            if (c[2] == c[3] || c[3] == c[4]) {
                return true
            }
        }

        if (c[1] == c[2]) {
            if (c[3] == c[4]) {
                return true
            }
        }

        return false
    }

    private fun hasTrips(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] == c[1] && c[1] == c[2]) {
            return true
        } else if (c[1] == c[2] && c[2] == c[3]) {
            return true
        } else if (c[2] == c[3] && c[3] == c[4]) {
            return true
        }

        return false
    }

    private fun hasStraight(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] + 1 == c[1]) {
            if (c[1] + 1 == c[2]) {
                if (c[2] + 1 == c[3]) {
                    if (c[3] + 1 == c[4]) {
                        return true
                    }
                }
            }
        }

        if (c[0] == 0 && c[1] == 1 && c[2] == 2 && c[3] == 3 && c[4] == 12) {
            return true
        }

        return false
    }

    private fun hasFlush(c: IntArray): Boolean {
        if (getCardColorID(c[0]) == getCardColorID(c[1])) {
            if (getCardColorID(c[1]) == getCardColorID(c[2])) {
                if (getCardColorID(c[2]) == getCardColorID(c[3])) {
                    if (getCardColorID(c[3]) == getCardColorID(c[4])) {
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun hasFullHouse(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] == c[1] && c[1] == c[2]) {
            if (c[3] == c[4]) {
                return true
            }
        }

        if (c[0] == c[1]) {
            if (c[2] == c[3] && c[3] == c[4]) {
                return true
            }
        }

        return false
    }

    private fun hasQuads(cd: IntArray): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(cd[i])
        }

        c.sort()

        if (c[0] == c[1] && c[1] == c[2] && c[2] == c[3]) {
            return true
        } else if (c[1] == c[2] && c[2] == c[3] && c[3] == c[4]) {
            return true
        }

        return false
    }

    private fun hasStraightFlush(c: IntArray): Boolean {
        if (hasFlush(c)) {
            if (hasStraight(c)) {
                return true
            }
        }

        return false
    }

    fun handPair(hand: IntArray): Boolean {
        if(getCardFigureID(hand[0]) == getCardFigureID(hand[1])){
            return true
        }

        return false
    }

    fun handMatchingColors(hand: IntArray): Boolean {
        if(getCardColorID(hand[0]) == getCardColorID(hand[1])){
            return true
        }

        return false
    }

    fun handJackOrAbove(hand: IntArray): Boolean {
        if(getCardFigureID(hand[0]) > 8 || getCardFigureID(hand[1]) > 8){
            return true
        }

        return false
    }

    fun handOneAfterAnother(hand: IntArray): Boolean {
        if(getCardFigureID(hand[0]) == getCardFigureID(hand[1]) + 1 || getCardFigureID(hand[0]) + 1 == getCardFigureID(hand[1])){
            return true
        }

        if(getCardFigureID(hand[0]) == 0 && getCardFigureID(hand[0]) == 12){
            return true
        }else if(getCardFigureID(hand[0]) == 12 && getCardFigureID(hand[0]) == 0){
            return true
        }

        return false
    }

    fun tableThreeCardsInColor(table: IntArray, round: Int): Boolean {
        when(round){
            1 ->{
                return getCardColorID(table[0]) == getCardColorID(table[1]) && getCardColorID(table[1]) == getCardColorID(table[2])
            }
            2 ->{
                for(i in 0..3){
                    if(tableThreeCardsInColor((intArrayOf(permutaions2[i][0], permutaions2[i][1], permutaions[i][2])), 1)){
                        return true
                    }
                }

                return false
            }
        }

        return false
    }

    fun tablePair(table: IntArray, round: Int): Boolean {
        val c = IntArray(5)
        for (i in 0..4) {
            c[i] = getCardFigureID(table[i])
        }

        c.sort()

        when(round){
            1 ->{
                if(c[0] == c[1] || c[1] == c[2]){
                    return true
                }
            }
            2 ->{
                if(c[0] == c[1] || c[1] == c[2] || c[2] == c[3]){
                    return true
                }
            }
        }

        return false
    }

    fun tableThreeCardsInColorAndOrder(table: IntArray, round: Int): Boolean {
        if(tableThreeCardsInColor(table, round)){
            val c = IntArray(5)
            for (i in 0..4) {
                c[i] = getCardFigureID(table[i])
            }

            c.sort()

            when(round){
                1 ->{
                    return c[0] == c[1] + 1 && c[1] == c[2] + 1
                }
                2 ->{

                    if(c[0] == c[1] + 1 && c[1] == c[2] + 1){
                        return true
                    }else if(c[1] == c[2] + 1 && c[2] == c[3] + 1){
                        return true
                    }

                    return false
                }
            }
        }

        return false
    }

    fun combinationS(hand: IntArray, table: IntArray, round: Int): Boolean{
        when(round){
            1 ->{
                val c = IntArray(5)
                c[0] = hand[0]
                c[1] = hand[1]
                for (i in 2..4) {
                    c[i] = getCardFigureID(table[i - 2])
                }

                c.sort()

                if(c[0] == c[1] + 1 && c[1] == c[2] + 1 && c[2] == c[3] + 1){
                    return true
                }else if(c[1] == c[2] + 1 && c[2] == c[3] + 1 && c[3] == c[4] + 1){
                    return true
                }

                return false
            }
            2 ->{
                val c = IntArray(5)
                c[0] = hand[0]
                c[1] = hand[1]
                for (i in 2..5) {
                    c[i] = getCardFigureID(table[i - 2])
                }

                c.sort()

                if(c[0] == c[1] + 1 && c[1] == c[2] + 1 && c[2] == c[3] + 1){
                    return true
                }else if(c[1] == c[2] + 1 && c[2] == c[3] + 1 && c[3] == c[4] + 1){
                    return true
                }else if(c[2] == c[3] + 1 && c[3] == c[4] + 1 && c[4] == c[5] + 1){
                    return true
                }

                return false
            }
        }

        return false
    }

    fun combinationF(hand: IntArray, table: IntArray, round: Int): Boolean{
        when(round){
            1 ->{
                val c = IntArray(5)
                c[0] = hand[0]
                c[1] = hand[1]
                for (i in 2..4) {
                    c[i] = getCardColorID(table[i - 2])
                }

                c.sort()

                if(c[0] == c[1] && c[1] == c[2] && c[2] == c[3]){
                    return true
                }else if(c[1] == c[2] && c[2] == c[3] && c[3] == c[4]){
                    return true
                }

                return false
            }
            2 ->{
                val c = IntArray(5)
                c[0] = hand[0]
                c[1] = hand[1]
                for (i in 2..5) {
                    c[i] = getCardColorID(table[i - 2])
                }

                c.sort()

                if(c[0] == c[1] && c[1] == c[2] && c[2] == c[3]){
                    return true
                }else if(c[1] == c[2] && c[2] == c[3] && c[3] == c[4]){
                    return true
                }else if(c[2] == c[3] && c[3] == c[4] && c[4] == c[5]){
                    return true
                }

                return false
            }
        }

        return false
    }

    //Funkcja oblicza podstawowy wynik ręki:
    //0 - Nic(wysoka karta)
    //1 - Para
    //2 - 2 pary
    //3 - Trójka
    //4 - Strit
    //5 - Kolor
    //6 - Full
    //7 - Kareta
    //8 - Poker

    private fun handScore(c: IntArray): IntArray {
        if (hasStraightFlush(c)) {
            return intArrayOf(8, c[0], c[1], c[2], c[3], c[4])
        } else if (hasQuads(c)) {
            return intArrayOf(7, c[0], c[1], c[2], c[3], c[4])
        } else if (hasFullHouse(c)) {
            return intArrayOf(6, c[0], c[1], c[2], c[3], c[4])
        } else if (hasFlush(c)) {
            return intArrayOf(5, c[0], c[1], c[2], c[3], c[4])
        } else if (hasStraight(c)) {
            return intArrayOf(4, c[0], c[1], c[2], c[3], c[4])
        } else if (hasTrips(c)) {
            return intArrayOf(3, c[0], c[1], c[2], c[3], c[4])
        } else if (hasDoubleDubs(c)) {
            return intArrayOf(2, c[0], c[1], c[2], c[3], c[4])
        } else if (hasDubs(c)) {
            return intArrayOf(1, c[0], c[1], c[2], c[3], c[4])
        }

        return intArrayOf(0, c[0], c[1], c[2], c[3], c[4])
    }

    fun judge(hand: IntArray, table: IntArray, botHelperRound: Int): IntArray {
        val combined = intArrayOf(hand[0], hand[1], table[0], table[1], table[2], table[3], table[4])


        var i = 0
        var best = handScore(
            intArrayOf(
                combined[permutaions[i][0]],
                combined[permutaions[i][1]],
                combined[permutaions[i][2]],
                combined[permutaions[i][3]],
                combined[permutaions[i][4]]
            )
        )

        i = 1

        var kk = 21

        when(botHelperRound){
            1 -> {
                kk = 1
            }
            2 -> {
                kk = 6
            }
        }

        while (i < kk) {
            val c = intArrayOf(
                combined[permutaions[i][0]],
                combined[permutaions[i][1]],
                combined[permutaions[i][2]],
                combined[permutaions[i][3]],
                combined[permutaions[i][4]]
            )

            val score = handScore(c)
            //System.out.println(Arrays.toString(score));


            if (score[0] > best[0]) {
                best = score
            } else if (score[0] == best[0]) {
                val temp1 = IntArray(5)
                val temp2 = IntArray(5)

                when (score[0]) {
                    0 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        if (temp1[4] > temp2[4]) {
                            best = score
                        } else if (temp1[4] == temp2[4]) {
                            if (temp1[3] > temp2[3]) {
                                best = score
                            } else if (temp1[3] == temp2[3]) {
                                if (temp1[2] > temp2[2]) {
                                    best = score
                                } else if (temp1[2] == temp2[2]) {
                                    if (temp1[1] > temp2[1]) {
                                        best = score
                                    } else if (temp1[1] == temp2[1]) {
                                        if (temp1[0] > temp2[0]) {
                                            best = score
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        for (j in 0..3) {
                            if (temp1[j] == temp1[j + 1]) {
                                val t1 = temp1[j]
                                val t2 = temp1[j + 1]
                                temp1[j] = temp1[0]
                                temp1[j + 1] = temp1[1]
                                temp1[0] = t1
                                temp1[1] = t2
                            }

                            if (temp2[j] == temp2[j + 1]) {
                                val t1 = temp2[j]
                                val t2 = temp2[j + 1]
                                temp2[j] = temp2[0]
                                temp2[j + 1] = temp2[1]
                                temp2[0] = t1
                                temp2[1] = t2
                            }
                        }

                        if (temp1[0] > temp2[0]) {
                            best = score
                        } else if (temp1[0] == temp2[0]) {
                            if (temp1[4] > temp2[4]) {
                                best = score
                            } else if (temp1[4] == temp2[4]) {
                                if (temp1[3] > temp2[3]) {
                                    best = score
                                } else if (temp1[3] == temp2[3]) {
                                    if (temp1[2] > temp2[2]) {
                                        best = score
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        val odd1: Int
                        val p11: Int
                        val p12: Int
                        val odd2: Int
                        val p21: Int
                        val p22: Int

                        if (temp1[0] != temp1[1]) {
                            odd1 = temp1[0]
                            p11 = temp1[1]
                            p21 = temp1[3]
                        } else if (temp1[2] != temp1[3]) {
                            odd1 = temp1[2]
                            p11 = temp1[0]
                            p21 = temp1[3]
                        } else {
                            odd1 = temp1[4]
                            p11 = temp1[0]
                            p21 = temp1[2]
                        }

                        if (temp2[0] != temp2[1]) {
                            odd2 = temp2[0]
                            p12 = temp2[1]
                            p22 = temp2[3]
                        } else if (temp2[2] != temp2[3]) {
                            odd2 = temp2[2]
                            p12 = temp2[0]
                            p22 = temp2[3]
                        } else {
                            odd2 = temp2[4]
                            p12 = temp2[0]
                            p22 = temp2[2]
                        }

                        if (p21 > p22) {
                            best = score
                        } else if (p21 == p22) {
                            if (p11 > p12) {
                                best = score
                            } else if (p11 == p12) {
                                if (odd1 > odd2) {
                                    best = score
                                }
                            }
                        }
                    }
                    3 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        val odd11: Int
                        val odd21: Int
                        val p1: Int
                        val odd12: Int
                        val odd22: Int
                        val p2: Int

                        if (temp1[0] == temp1[1] && temp1[1] == temp1[2]) {
                            p1 = temp1[0]
                            odd11 = temp1[3]
                            odd21 = temp1[4]
                        } else if (temp1[1] == temp1[2] && temp1[2] == temp1[3]) {
                            odd11 = temp1[0]
                            p1 = temp1[1]
                            odd21 = temp1[4]
                        } else {
                            odd11 = temp1[0]
                            odd21 = temp1[1]
                            p1 = temp1[2]
                        }

                        if (temp2[0] == temp2[1] && temp2[1] == temp2[2]) {
                            p2 = temp2[0]
                            odd12 = temp2[3]
                            odd22 = temp2[4]
                        } else if (temp2[1] == temp2[2] && temp2[2] == temp2[3]) {
                            odd12 = temp2[0]
                            p2 = temp2[1]
                            odd22 = temp2[4]
                        } else {
                            odd12 = temp2[0]
                            odd22 = temp2[1]
                            p2 = temp2[2]
                        }

                        if (p1 > p2) {
                            best = score
                        } else if (p1 == p2) {
                            if (odd21 > odd22) {
                                best = score
                            } else if (odd11 > odd12) {
                                best = score
                            }
                        }
                    }
                    4 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        if (temp1[0] != 0) {
                            if (temp1[4] > temp2[4]) {
                                best = score
                            }
                        }
                    }
                    5 -> {
                        for (j in 0..4) {
                            temp1[j] = c[j]
                            temp2[j] = best[j + 1]
                        }

                        temp1.sort()
                        temp2.sort()

                        if (temp1[4] > temp2[4]) {
                            best = score
                        } else if (temp1[4] == temp2[4]) {
                            if (temp1[3] > temp2[3]) {
                                best = score
                            } else if (temp1[3] == temp2[3]) {
                                if (temp1[2] > temp2[2]) {
                                    best = score
                                } else if (temp1[2] == temp2[2]) {
                                    if (temp1[1] > temp2[1]) {
                                        best = score
                                    } else if (temp1[1] == temp2[1]) {
                                        if (temp1[0] > temp2[0]) {
                                            best = score
                                        }
                                    }
                                }
                            }
                        }
                    }
                    6 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        val t1: Int
                        val t2: Int
                        val pp1: Int
                        val pp2: Int

                        if (temp1[0] == temp1[1] && temp1[1] == temp1[2]) {
                            t1 = temp1[0]
                            pp1 = temp1[3]
                        } else {
                            t1 = temp1[2]
                            pp1 = temp1[0]
                        }

                        if (temp2[0] == temp2[1] && temp2[1] == temp2[2]) {
                            t2 = temp2[0]
                            pp2 = temp2[3]
                        } else {
                            t2 = temp2[2]
                            pp2 = temp2[0]
                        }

                        if (t1 > t2) {
                            best = score
                        } else if (t1 == t2) {
                            if (pp1 > pp2) {
                                best = score
                            }
                        }
                    }
                    7 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        val q1: Int
                        val kicker: Int

                        val q2: Int
                        val kicker2: Int

                        if (temp1[0] == temp1[1] && temp1[1] == temp1[2] && temp1[2] == temp1[3]) {
                            q1 = temp1[0]
                            kicker = temp1[4]
                        } else {
                            kicker = temp1[0]
                            q1 = temp1[1]
                        }

                        if (temp2[0] == temp2[1] && temp2[1] == temp2[2] && temp2[2] == temp2[3]) {
                            q2 = temp2[0]
                            kicker2 = temp2[4]
                        } else {
                            kicker2 = temp2[0]
                            q2 = temp2[1]
                        }

                        if (q1 > q2) {
                            best = score
                        } else if (q1 == q2) {
                            if (kicker > kicker2) {
                                best = score
                            }
                        }
                    }
                    8 -> {
                        for (j in 0..4) {
                            temp1[j] = getCardFigureID(c[j])
                            temp2[j] = getCardFigureID(best[j + 1])
                        }

                        temp1.sort()
                        temp2.sort()

                        if (temp1[0] != 0) {
                            if (temp1[4] > temp2[4]) {
                                best = score
                            }
                        }
                    }
                }
            }
            i++
        }
        return best
    }

    //Funkcja uzupełnia podstawowy wynik aby móc roztrzygać remisy:
    //Wygrywa ręka z wyzszymi kolejnymi cechami, zaczynająć od podstawowego wyniku, kończąc na kickerach
    //0 - {0, kicker1, kicker2, kicker3, kicker4, kicker5}
    //1 - {1, para1, kicker1, kicker2, kicker3}
    //2 - {2, para1, para2, kicker1}
    //3 - {3, trójka1, kicker1, kicker2}
    //4 - {4, top}
    //5 - {5, kicker1, kicker2, kicker3, kicker4, kicker5}
    //6 - {6, trójka1, para1}
    //7 - {7, kareta1, kicker1}
    //8 - {8, top}

    //top - najwyższa karta w stricie / pokerze (dla strita / pokera od asa do 5 jest to 5)

    fun translate(verdict: IntArray): IntArray {
        val temp = IntArray(5)

        for (j in 0..4) {
            temp[j] = getCardFigureID(verdict[j + 1])
        }

        temp.sort()

        when (verdict[0]) {
            0 -> return intArrayOf(verdict[0], temp[4], temp[3], temp[2], temp[1], temp[0])
            1 -> return if (temp[0] == temp[1]) {
                intArrayOf(verdict[0], temp[0], temp[4], temp[3], temp[2])
            } else if (temp[1] == temp[2]) {
                intArrayOf(verdict[0], temp[1], temp[4], temp[3], temp[0])
            } else if (temp[2] == temp[3]) {
                intArrayOf(verdict[0], temp[2], temp[4], temp[1], temp[0])
            } else {
                intArrayOf(verdict[0], temp[3], temp[2], temp[1], temp[0])
            }
            2 -> return if (temp[0] == temp[1]) {
                if (temp[2] == temp[3]) {
                    intArrayOf(verdict[0], temp[2], temp[0], temp[4])
                } else {
                    intArrayOf(verdict[0], temp[3], temp[0], temp[2])
                }
            } else {
                intArrayOf(verdict[0], temp[3], temp[1], temp[0])
            }
            3 -> return if (temp[0] == temp[1] && temp[1] == temp[2]) {
                intArrayOf(verdict[0], temp[0], temp[4], temp[3])
            } else if (temp[1] == temp[2] && temp[2] == temp[3]) {
                intArrayOf(verdict[0], temp[1], temp[4], temp[0])
            } else {
                intArrayOf(verdict[0], temp[2], temp[1], temp[0])
            }
            4 -> return if (temp[0] == 0 && temp[4] == 12) {
                intArrayOf(verdict[0], temp[3])
            } else {
                intArrayOf(verdict[0], temp[4])
            }
            5 -> return intArrayOf(verdict[0], temp[4], temp[3], temp[2], temp[1], temp[0])
            6 -> return if (temp[0] == temp[1] && temp[1] == temp[2]) {
                intArrayOf(verdict[0], temp[0], temp[3])
            } else {
                intArrayOf(verdict[0], temp[2], temp[0])
            }
            7 -> return if (temp[0] == temp[1] && temp[1] == temp[2] && temp[2] == temp[3]) {
                intArrayOf(verdict[0], temp[0], temp[4])
            } else {
                intArrayOf(verdict[0], temp[1], temp[0])
            }
            else -> return if (temp[0] == 0 && temp[4] == 12) {
                intArrayOf(verdict[0], temp[3])
            } else {
                intArrayOf(verdict[0], temp[4])
            }
        }
    }

    //Tiebreaker score to liczba w systemie 13, obliczana na podstawie siły układu (im mocniejszy układ tym wyższy wynik)

    fun getTiebreakerScore(p: IntArray, length: Int): Int {
        var ts = 0

        for(i in p.indices){
            ts = ts * 13 + p[i]
        }

        for(i in p.size until length){
            ts *= 13
        }

        return ts
    }

    //Funkcje przyjmują (w zależności od nazwy) 2, 3 lub 4 wyniki i decydują kolejność wygranych

    //Kolejność wygranych: bliżej lewej - lepsza ręka, = oznacza remis pomiędzy tymi graczami

    fun twoPeopleVictoryOrder(p1: IntArray, p2: IntArray): String {
        for (i in p1.indices) {
            if (p1[i] > p2[i]) {
                return "1 2"
            } else if (p1[i] < p2[i]) {
                return "2 1"
            }
        }

        return "1=2"
    }

    fun threePeopleVictoryOrder(p1: IntArray, p2: IntArray, p3: IntArray): String {
        var size = max(p1.size, p2.size)
        size = max(size, p3.size)

        var tscores = intArrayOf(
            getTiebreakerScore(p1, size),
            getTiebreakerScore(p2, size),
            getTiebreakerScore(p3, size)
        )

        return tieSolver(tscores, intArrayOf(1,2,3))
    }

    fun fourPeopleVictoryOrder(p1: IntArray, p2: IntArray, p3: IntArray, p4: IntArray): String {
        var size = max(p1.size, p2.size)
        size = max(size, p3.size)
        size = max(size, p4.size)

        var tscores = intArrayOf(
            getTiebreakerScore(p1, size),
            getTiebreakerScore(p2, size),
            getTiebreakerScore(p3, size),
            getTiebreakerScore(p4, size)
        )

        return tieSolver(tscores, intArrayOf(1,2,3,4))
    }

    companion object {

        //Dla wszystkich 21 możliwości (symbol newtona 7 z 5) sprawdzany jest najmocniejszy układ kart

        private val permutaions = arrayOf(
            intArrayOf(0, 1, 2, 3, 4),
            intArrayOf(0, 1, 2, 3, 5),
            intArrayOf(0, 1, 2, 4, 5),
            intArrayOf(0, 1, 3, 4, 5),
            intArrayOf(0, 2, 3, 4, 5),
            intArrayOf(1, 2, 3, 4, 5),
            intArrayOf(0, 1, 2, 3, 6),
            intArrayOf(0, 1, 2, 4, 6),
            intArrayOf(0, 1, 2, 5, 6),
            intArrayOf(0, 1, 3, 4, 6),
            intArrayOf(0, 1, 3, 5, 6),
            intArrayOf(0, 1, 4, 5, 6),
            intArrayOf(0, 2, 3, 4, 6),
            intArrayOf(0, 2, 3, 5, 6),
            intArrayOf(0, 2, 4, 5, 6),
            intArrayOf(0, 3, 4, 5, 6),
            intArrayOf(1, 2, 3, 4, 6),
            intArrayOf(1, 2, 3, 5, 6),
            intArrayOf(1, 2, 4, 5, 6),
            intArrayOf(1, 3, 4, 5, 6),
            intArrayOf(2, 3, 4, 5, 6)
        )

        private val permutaions2 = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(0, 1, 3),
            intArrayOf(0, 2, 3),
            intArrayOf(1, 2, 3)
        )
    }
}