package com.codesai.katas.eventSourcing

import java.time.LocalDateTime

sealed interface BaseEvent {
    val id: String
    val timestamp : LocalDateTime
}

sealed interface DomainEvent : BaseEvent
sealed interface DeprecatedEvent : BaseEvent {
    fun toLastVersion() : DomainEvent
}

data class AuctionCreated(override val id: String, val itemDescription: String, val initialPrice: Int,
                          override val timestamp: LocalDateTime = LocalDateTime.now()) : DomainEvent

data class AuctionNewBidV2(override val id: String, val newBid : Int, val bidder: String,
    override val timestamp: LocalDateTime = LocalDateTime.now()) : DomainEvent

data class AuctionClosed(override val id: String, val hasWinner: Boolean,
                         override val timestamp: LocalDateTime = LocalDateTime.now()) : DomainEvent

data class AuctionNewBid(override val id: String, val newBid : Int,
                         override val timestamp: LocalDateTime = LocalDateTime.now() ) : DeprecatedEvent {
    override fun toLastVersion() = AuctionNewBidV2(this.id, this.newBid, "")
}
