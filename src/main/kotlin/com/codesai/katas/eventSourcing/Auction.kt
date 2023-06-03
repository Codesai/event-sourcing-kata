package com.codesai.katas.eventSourcing

import java.util.UUID

class Auction {

    var id: String = ""
    var itemDescription: String = ""
    var initialPrice: Int = 0

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

    private fun applyEvent(event: BaseEvent) {
        when(event) {
            is AuctionCreated -> applyEvent(event)
        }
    }

    private fun applyEvent(event: AuctionCreated) {
        this.id = event.id
        this.itemDescription = event.itemDescription
        this.initialPrice = event.initialPrice
    }
}
