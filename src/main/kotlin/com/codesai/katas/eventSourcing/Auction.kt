package com.codesai.katas.eventSourcing

import java.util.*

class Auction {

    var id: String = ""
    var itemDescription: String = ""
    var initialPrice: Int = 0
    var currentBid: Int = 0
    var closed : Boolean = false
    var hasWinner: Boolean = false

    val changes : MutableList<BaseEvent> = mutableListOf()

    constructor(itemDescription: String, initialPrice: Int) {
        this.id = UUID.randomUUID().toString()
        this.itemDescription = itemDescription
        this.initialPrice = initialPrice
        changes.add(AuctionCreated(id, itemDescription, initialPrice))
    }

    constructor(events : List<DomainEvent>) {
        events.forEach { applyEvent(it) }
    }

    fun bid(newBid: Int) {
        if (newBid < currentBid) throw RuntimeException("bid must be greater than $currentBid")

        val event = AuctionNewBid(this.id, newBid)

        applyEvent(event)
    }

    fun close() {
        applyEvent(AuctionClosed(this.id, this.currentBid != 0))
    }

    fun applyEvent(event: DomainEvent) {
        when(event) {
            is AuctionCreated -> applyEvent(event)
            is AuctionNewBidV2 -> applyEvent(event)
            is AuctionClosed -> applyEvent(event)
        }
    }

    private fun applyEvent(event: AuctionNewBid) {
        this.currentBid = event.newBid
    }

    private fun applyEvent(event: AuctionClosed) {
        this.closed = true
        this.hasWinner = event.hasWinner
    }

    private fun applyEvent(event: AuctionCreated) {
        this.id = event.id
        this.itemDescription = event.itemDescription
        this.initialPrice = event.initialPrice
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Auction

        if (id != other.id) return false
        if (itemDescription != other.itemDescription) return false
        if (initialPrice != other.initialPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + itemDescription.hashCode()
        result = 31 * result + initialPrice
        return result
    }
}
