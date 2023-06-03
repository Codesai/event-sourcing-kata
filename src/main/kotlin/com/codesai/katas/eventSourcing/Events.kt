package com.codesai.katas.eventSourcing

sealed interface BaseEvent {
    val id: String
}

data class AuctionCreated(override val id: String, val itemDescription: String, val initialPrice: Int) : BaseEvent

