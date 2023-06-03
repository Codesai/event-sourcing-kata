package com.codesai.katas.eventSourcing

import java.util.*

class Auction {

    var id: String = ""
    var itemDescription: String = ""
    var initialPrice: Int = 0
    var currentBid: Int = 0

    val changes : MutableList<BaseEvent> = mutableListOf()

    constructor(itemDescription: String, initialPrice: Int) {
        this.id = UUID.randomUUID().toString()
        this.itemDescription = itemDescription
        this.initialPrice = initialPrice
        changes.add(AuctionCreated(id, itemDescription, initialPrice))
    }

    constructor(events : List<BaseEvent>) {
        events.forEach { applyEvent(it) }
    }

    fun bid(newBid: Int) {
        if (newBid < currentBid) throw RuntimeException("bid must be greater than $currentBid")

        val event = AuctionNewBid(this.id, newBid)

        applyEvent(event)
    }

    private fun applyEvent(event: BaseEvent) {
        when(event) {
            is AuctionCreated -> applyEvent(event)
            is AuctionNewBid -> applyEvent(event)
        }
    }

    private fun applyEvent(event: AuctionNewBid) {
        this.currentBid = event.newBid
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
