package hr.algebra.latestbitcoinprice.model

import java.time.LocalDateTime

data class Item(
    val rate: String,
    val timestamp: String,
    val timestampISO: String
) : Comparable<Item>{
    override fun toString(): String {
        return "$rate - updated:  $timestamp"
    }

    override fun compareTo(other: Item): Int {
        val firstTime = LocalDateTime.parse(this.timestampISO)
        val secondTime = LocalDateTime.parse(other.timestampISO)
        return secondTime.compareTo(firstTime)
    }
}
