package com.pwr.amproject.logic

import com.pwr.amproject.BotLogic.Deck

class Coordinator(
    private val bigBlind: Int,
    private val smallBlind: Int,
    private val maxPlayers: Int,
    private val maxBots: Int
) {
    private var deck = Deck()
    private var hands: MutableMap<Int, Pair<String, String>> = HashMap()
    private lateinit var table: List<String>

    /**
     * Map<PlayerID, StatusID>
     * Statusy:
     * -1 - empty
     * 0 - player (lost)
     * 1 - player (playing)
     * 10 - player (won)
     * 2 - bot (lost)
     * 3 - bot (playing)
     * 32 - bot (won)
     */
    private var playerStatus: MutableMap<Int, Int> = hashMapOf(Pair(1, -1), Pair(2, -1), Pair(3, -1), Pair(4, -1))

    /**
     * (currentRound, currentPhase)
     * (0, 0) - start nowej rundy - rozdanie graczom świeżych kart
     * (0, 1) - faza pre-licytacji przed pokazaniem kart na stole
     * (1, 0) - wydanie pierwszych 3 kart na stół
     * (1, 1) - licytacja
     * (2, 0) - 4-ta karta
     * (2, 1) - licytacja
     * (3, 0) - 5-ta karta
     * (3, 1) - licytacja
     * (4, 0) - koniec rundy
     * (5, 0) - koniec rozgrywki
     */
    private var currentRound: Int = 0
    private var currentPhase: Int = 0

    private lateinit var winners: List<String>

    private var currentPlayerId: Int = 0
    private var dealerId: Int = 0
    private var smallBlindId: Int = 1
    private var bigBlindId: Int = 2

    private var biddingPool: Int = 0
    private var lastRaise: Int = bigBlind

    /**
     * -1 - brak ruchu gracza
     * 0 - fold
     * 1 - check/call
     * 2+ - raise/all-in (wartość)
     */
    private var lastMove: Int = -1

    private var serverMessage: String = ""
    private var clientMessage: String = ""


    fun roundSwitcher() {
        when (currentRound) {
            0 -> {
                when (currentPhase) {
                    0 -> {
                        startRound()
                        setStartRoundMessage(1)
                        nextPhase()
                    }
                    1 -> {
                        bid()
                        nextPhase()
                    }
                }
            }
            1 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(3)
                        nextPhase()
                    }
                    1 -> {
                        bid()
                        nextPhase()
                    }
                }
            }
            2 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(1)
                        nextPhase()
                    }
                    1 -> {
                        bid()
                        nextPhase()
                    }
                }
            }
            3 -> {
                when (currentPhase) {
                    0 -> {
                        showCards(1)
                        nextPhase()
                    }
                    1 -> {
                        bid()
                        nextPhase()
                    }
                }
            }
            4 -> {
                finishRound()
            }
            5 -> {
                finishGame()
            }
        }
    }

    private fun startRound() {
        val allCards = deck.getCards(maxPlayers).split(" ")
        hands[1] = Pair(allCards[0], allCards[1])
        hands[2] = Pair(allCards[2], allCards[3])
        table = allCards.subList(4, 9)
        winners = allCards.subList(10, allCards.size)
        if (maxBots > 0) {
            for (i in 1..maxBots) {
                playerStatus[i] = 3
            }
            for (i in (maxBots + 1)..(maxBots + maxPlayers)) {
                playerStatus[i] = 1
            }
        } else {
            for (i in 1..maxPlayers) {
                playerStatus[i] = 1
            }
        }

        println(allCards)
        println(playerStatus)
        if (maxPlayers == 3) {
            hands[3] = Pair(allCards[4], allCards[5])
            table = allCards.subList(6, 11)
            winners = allCards.subList(12, allCards.size)
        }
        if (maxPlayers == 4) {
            hands[4] = Pair(allCards[6], allCards[7])
            table = allCards.subList(8, 13)
            winners = allCards.subList(14, allCards.size)
        }
    }

    private fun showCards(numberOfCards: Int) {

    }

    private fun finishRound() {

    }

    private fun getBidMessage() {
        // TODO get message from client after his move
    }

    private fun setBidMessage(playerID: Int) {
        serverMessage = "$playerID " +
                "$currentRound " +
                "$currentPhase " +
                "${hands[playerID]!!.first} " +
                "${hands[playerID]!!.second} " +
                "$biddingPool " +
                "$lastRaise " +
                "$lastMove "
    }

    private fun setStartRoundMessage(playerID: Int) {
        serverMessage = "$playerID " +
                "$currentRound " +
                "$currentPhase " +
                "${hands[playerID]!!.first} " +
                "${hands[playerID]!!.second} " +
                "$dealerId " +
                "$smallBlindId " +
                "$bigBlindId "
    }

    private fun bid() {
        while (!allPlayersMoved()) {
            setBidMessage(currentPlayerId)
            sendMessage()
            waitForAnswer()
            currentPlayerId++
        }
    }

    private fun waitForAnswer() {

    }

    private fun sendMessage() {

    }

    private fun allPlayersMoved(): Boolean {
        return currentPlayerId == maxPlayers
    }

    private fun nextPhase() {
        currentPhase++
    }

    private fun finishGame() {
        var multipleWinners: List<Int>
        if (winners[0].contains("=")) {
            multipleWinners = winners[0].split("=").map { it.toInt() }
        } else {
            multipleWinners = mutableListOf(winners[0].toInt())
        }
        for (i in 1..playerStatus.size) {
            if (multipleWinners.contains(i)) {
                setWinStatus(i)
            } else {
                setLostStatus(i)
            }
        }
        clear()
    }

    private fun setLostStatus(playerId: Int) {
        if (playerStatus[playerId] == 3) {
            playerStatus[playerId] = 2
        } else if (playerStatus[playerId] == 1) {
            playerStatus[playerId] = 0
        }
        setFinalMessage(playerId, "W $biddingPool")
        sendMessage()
    }

    private fun setWinStatus(playerId: Int) {
        if (playerStatus[playerId] == 3) {
            playerStatus[playerId] = 32
        } else if (playerStatus[playerId] == 1) {
            playerStatus[playerId] = 12
        }
        setFinalMessage(playerId, "L")
        sendMessage()
    }

    private fun clear() {
        deck = Deck()
        hands = HashMap()
        table = ArrayList()
        dealerId = 0
        smallBlindId = 1
        bigBlindId = 2
        currentRound = 0
        currentPlayerId = 0
        biddingPool = 0
    }

    private fun setFinalMessage(playerID: Int, result: String) {
        serverMessage = "$playerID " +
                "$currentRound " +
                "$currentPhase " +
                result
    }
}
