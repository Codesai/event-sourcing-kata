package com.codesai.katas.eventSourcing
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class EventSourcingKataTest {

    private val auctionRepository : AuctionRepository = AuctionRepositoryEventSourcing()

    @Test
    fun `create an auction`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id).shouldBeEqual(auction)
    }

    @Test
    fun `new bid`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        auction.bid(auction.initialPrice + 100)

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id).shouldBeEqual(auction)
    }

    @Test
    fun `close auction`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        auction.bid(auction.initialPrice + 100)
        auction.close()

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id).shouldBeEqual(auction)
    }

    @Test
    fun `snapshot repository create snapshot every 5 events`() {
        val auctionRepository = mockk<AuctionRepositoryEventSourcing>(relaxed = true)
        val auctionRepositorySnapshot = AuctionRepositorySnapshot(auctionRepository)

        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        (1..5).forEach { index ->
            auction.bid(auction.initialPrice + index)
            auctionRepositorySnapshot.save(auction)
        }

        auctionRepositorySnapshot.hasSnapshotFor(auction.id)
    }

    class AuctionRepositorySnapshot(val auctionRepository: AuctionRepositoryEventSourcing) : AuctionRepository {

        private val SAVE_SNAPSHOT = 5

        val eventCounter = HashMap<String, Int>()
        val snapshots = HashMap<String, Pair<Auction, LocalDateTime>>()

        override fun save(auction: Auction) {
            val events = eventCounter.getOrDefault(auction.id, 0)

            if (events + auction.changes.size > SAVE_SNAPSHOT) {
                snapshots[auction.id] = Pair(auction, LocalDateTime.now())
            }

            eventCounter[auction.id] = (events + auction.changes.size) % SAVE_SNAPSHOT

            auctionRepository.save(auction)
        }

        override fun getById(id: String): Auction {
            return snapshots[id]?.let { (auction, timestamp) ->
                val events = auctionRepository.aggregateEvents(id).filter { it.timestamp > timestamp  }
                events.forEach {auction.applyEvent(it)}
                auction
            } ?: auctionRepository.getById(id)
        }

        fun hasSnapshotFor(id: String) = snapshots.containsKey(id)
    }

}


