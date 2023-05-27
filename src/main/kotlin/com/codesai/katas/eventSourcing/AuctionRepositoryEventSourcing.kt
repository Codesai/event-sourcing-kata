package com.codesai.katas.eventSourcing

open class AuctionRepositoryEventSourcing {

    internal val inMemoryEvents : MutableList<BaseEvent> = mutableListOf()

    fun save(auction: Auction) {
        inMemoryEvents.addAll(auction.changes)
    }

    fun getById(id: String): Auction {
        val aggregateEvents = inMemoryEvents.filter { event -> event.id == id }
        return Auction(aggregateEvents)
    }
}
