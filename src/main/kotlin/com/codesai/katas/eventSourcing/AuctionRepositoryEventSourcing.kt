package com.codesai.katas.eventSourcing

class AuctionRepositoryEventSourcing {

    private val inMemoryEvents : MutableList<BaseEvent> = mutableListOf()

    fun save(auction: Auction) {
        inMemoryEvents.addAll(auction.changes)
    }

    fun getById(id: String): Auction {
        val aggregateEvents = inMemoryEvents.filter { event -> event.id == id }
        return Auction(
            created = aggregateEvents.first() as AuctionCreated,
            events = aggregateEvents.drop(1)
        )
    }
}
