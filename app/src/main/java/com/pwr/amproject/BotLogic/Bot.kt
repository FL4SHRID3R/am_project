package com.pwr.amproject.BotLogic

import com.pwr.amproject.Table
import kotlin.random.Random

//Przy implementcji bota nalezy podać mu ilość środków na start
class Bot(stackSize: Int){
    private var sSize = stackSize
    private var j = Judge()

    private var hand = IntArray(2)
    private var table = IntArray(5)

    //Funkcja przyjmuje ręke, na której podstawie bot będzie podejmował decyzje
    //Bot podejmuje decyzje jedynie na podstawie kart które "widzi" (pomimo posiadania wszystkich kart stołu)
    fun takeHand(h: IntArray, t: IntArray){
        hand = h
        table = t
    }

    private fun bestPossibleHand(THREE_CARDS_IN_COLOR_AND_ORDER: Boolean, THREE_CARDS_IN_COLOR: Boolean, PAIR_ON_BOARD: Boolean): Int{
        if(THREE_CARDS_IN_COLOR_AND_ORDER){
            return 8
        }else if(PAIR_ON_BOARD){
            return 7
        }else if(THREE_CARDS_IN_COLOR){
            return 5
        }else{
            return 4
        }
    }

    private fun myHande(round: Int): Int{
        if(round == 1){
            return j.translate(j.judge(hand, table, 1))[0]
        } else if(round == 2){
            return j.translate(j.judge(hand, table, 2))[0]
        }else if(round == 3){
            return j.translate(j.judge(hand, table, 99))[0]
        }

        return 0
    }

    //Funkcja zwraca decyzje bota podjętą dzięki podanym parametrom
    //0 - fold
    //1 - check/call
    //2 i więcej - raise o zwróconą wartość
    //w przypadku calli / raisów / wejścia all in odpowiednia suma jest potrącana z balansu bota
    fun decision(round: Int, currentBet: Int, minBetSize: Int): Int{
        var foldChance = 0
        var checkChance = 0
        var raiseChance = 0

        val HAND_PAIR = j.handPair(hand)
        val HAND_MATCHING_COLORS = j.handMatchingColors(hand)
        val HAND_JACK_OR_ABOVE = j.handJackOrAbove(hand)
        val HAND_ONE_AFTER_ANOTHER = j.handOneAfterAnother(hand)

        val TABLE_THREE_CARDS_IN_COLOR_AND_ORDER = j.tableThreeCardsInColorAndOrder(table, round)
        val TABLE_THREE_CARDS_IN_COLOR = j.tableThreeCardsInColor(table, round)
        val TABLE_PAIR = j.tablePair(table, round)

        val COMBINATION_MISSING_CARD_STRAIGHT = j.combinationS(hand, table, round)
        val COMBINATION_MISSING_CARD_FLUSH = j.combinationF(hand, table, round)

        val myHand = myHande(round)
        val bestHand = bestPossibleHand(TABLE_THREE_CARDS_IN_COLOR_AND_ORDER, TABLE_THREE_CARDS_IN_COLOR, TABLE_PAIR)

        when(round){
            0 -> {
                if(HAND_PAIR && HAND_JACK_OR_ABOVE){
                    raiseChance += 9
                    checkChance += 1
                }else if(HAND_PAIR){
                    raiseChance += 2
                    checkChance += 8
                }else if(HAND_MATCHING_COLORS && HAND_JACK_OR_ABOVE && HAND_ONE_AFTER_ANOTHER){
                    raiseChance += 2
                    checkChance += 8

                    if(sSize < 4 * currentBet){
                        foldChance += 1
                    }
                }else if(HAND_JACK_OR_ABOVE && HAND_ONE_AFTER_ANOTHER){
                    checkChance += 10

                    if(sSize < 4 * currentBet){
                        foldChance += 1
                    }
                }else if(HAND_MATCHING_COLORS || HAND_ONE_AFTER_ANOTHER){
                    checkChance += 8
                    foldChance += 2

                    if(sSize < 4 * currentBet){
                        foldChance += 6
                    }
                }else{
                    checkChance += 5
                    foldChance += 5

                    if(sSize < 4 * currentBet){
                        foldChance += 10
                    }
                }
            }
            1 -> {
                if(myHand > 3){
                    raiseChance += 3
                    checkChance += 7
                }else if(bestHand - myHand < 2){
                    raiseChance += 1
                    checkChance += 9
                }else if(COMBINATION_MISSING_CARD_FLUSH){
                    checkChance += 10
                }else if(COMBINATION_MISSING_CARD_STRAIGHT){
                    if(TABLE_THREE_CARDS_IN_COLOR){
                        checkChance += 5

                        if(sSize < 5 * currentBet){
                            foldChance += 5
                        }
                    }else if(TABLE_PAIR){
                        checkChance += 5

                        if(sSize < 5 * currentBet){
                            foldChance += 5
                        }
                    }
                }else{
                    checkChance += 3
                    foldChance += 7

                    if(sSize < 5 * currentBet){
                        foldChance += 10
                    }
                }
            }
            2 -> {
                if(bestHand - myHand < 2){
                    raiseChance += 1
                    checkChance += 9
                }else if(COMBINATION_MISSING_CARD_FLUSH){
                    checkChance += 8
                    if(sSize < 5 * currentBet){
                        foldChance += 2
                    }
                }else if(COMBINATION_MISSING_CARD_STRAIGHT){
                    if(TABLE_THREE_CARDS_IN_COLOR){
                        checkChance += 6
                        foldChance += 4

                        if(sSize < 5 * currentBet){
                            foldChance += 10
                        }
                    }else if(TABLE_PAIR){
                        checkChance += 5
                        foldChance += 5

                        if(sSize < 5 * currentBet){
                            foldChance += 10
                        }
                    }
                }else{
                    checkChance += 3
                    foldChance += 7

                    if(sSize < 5 * currentBet){
                        foldChance += 10
                    }
                }
            }
            3 -> {
                if(bestHand - myHand < 2){
                    raiseChance += 2
                    checkChance += 8
                }else if (bestHand - myHand < 4){
                    checkChance += 4
                    foldChance += 6

                    if(sSize < 5 * currentBet){
                        foldChance += 10
                    }
                }else{
                    checkChance += 1
                    foldChance += 9

                    if(sSize < 5 * currentBet){
                        foldChance += 10
                    }
                }
            }
        }

        val sum = checkChance + raiseChance + foldChance

        val x = Random.nextInt(0, sum)

        if(x < foldChance){
            if(currentBet == 0){
                return 1
            }else{
                return 0
            }
        }else if(x < foldChance + checkChance){
            if(currentBet >= sSize) {
                sSize = 0
                return sSize
            }else{
                return 1
            }
        }else{
            if(currentBet >= sSize){
                sSize = 0
                return sSize
            }else{
                sSize -= minBetSize
                return minBetSize
            }
        }
    }

    //Funkcja pozwalająca przekazać fundusze botowi (na przykład po wygranej grze)
    fun giveMoney(amount: Int){
        sSize += amount
    }

    //Funkcja pozwalająca oszacować stan konta bota
    fun balance(): Int{
        return sSize;
    }
}