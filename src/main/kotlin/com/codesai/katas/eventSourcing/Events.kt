package com.codesai.katas.eventSourcing

sealed interface BaseEvent {
    val id: String
}

data class AuctionCreated(override val id: String, val itemDescription: String, val initialPrice: Int) : BaseEvent
data class AuctionNewBid(override val id: String, val newBid : Int ) : BaseEvent
data class AuctionClosed(override val id: String, val hasWinner: Boolean) : BaseEvent
