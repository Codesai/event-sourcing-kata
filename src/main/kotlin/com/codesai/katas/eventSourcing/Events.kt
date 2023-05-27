package com.codesai.katas.eventSourcing

sealed interface BaseEvent {
    val id: String
}

sealed interface DomainEvent : BaseEvent
sealed interface DeprecatedEvent : BaseEvent {
    fun toLastVersion() : DomainEvent
}

data class AuctionCreated(override val id: String, val itemDescription: String, val initialPrice: Int) : DomainEvent
data class AuctionNewBidV2(override val id: String, val newBid : Int, val bidder: String ) : DomainEvent
data class AuctionClosed(override val id: String, val hasWinner: Boolean) : DomainEvent

data class AuctionNewBid(override val id: String, val newBid : Int ) : DeprecatedEvent {
    override fun toLastVersion() = AuctionNewBidV2(this.id, this.newBid, "")
}
