package com.codesai.katas.eventSourcing

interface AuctionRepository {
    fun save(auction: Auction)
    fun getById(id: String): Auction
}

class AuctionRepositoryEventSourcing : AuctionRepository {

    private val inMemoryEvents : MutableList<BaseEvent> = mutableListOf()

    override fun save(auction: Auction) {
        inMemoryEvents.addAll(auction.changes)
    }

    override fun getById(id: String): Auction {
        val aggregateEvents = aggregateEvents(id)

        return Auction(aggregateEvents)
    }

    fun aggregateEvents(id: String): List<DomainEvent> {
        val aggregateEvents = inMemoryEvents
            .filter { event -> event.id == id }
            .map {
                when (it) {
                    is DeprecatedEvent -> it.toLastVersion()
                    is DomainEvent -> it
                }
            }
        return aggregateEvents
    }
}
