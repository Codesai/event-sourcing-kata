package com.codesai.katas.eventSourcing

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.util.*

class Auction {

    var id: String = ""
    var itemDescription: String = ""
    var initialPrice: Int = 0
    var currentBid: Int = 0
    var bidder: String = ""
    var closed : Boolean = false
    var hasWinner: Boolean = false

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

    fun bid(newBid: Int, bidder: String) {
        if (newBid < currentBid) throw RuntimeException("bid must be greater than $currentBid")

        val event = AuctionNewBidV2(this.id, newBid, bidder)

        applyEvent(event)

        changes.add(event)
    }

    fun close() {
        val event = AuctionClosed(this.id, this.currentBid != 0)

        applyEvent(event)

        changes.add(event)
    }

    private fun applyEvent(event: BaseEvent) {
        when(event) {
            is AuctionCreated -> applyEvent(event)
            is AuctionNewBid -> applyEvent(event)
            is AuctionNewBidV2 -> applyEvent(event)
            is AuctionClosed -> applyEvent(event)
        }
    }

    private fun applyEvent(event: AuctionNewBid) {
        this.currentBid = event.newBid
    }

    private fun applyEvent(event: AuctionNewBidV2) {
        this.bidder = event.bidder
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

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
