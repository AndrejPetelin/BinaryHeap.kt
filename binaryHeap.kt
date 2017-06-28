/**
 * BinaryHeap.kt
 * A binary heap data structure.
 *
 * TODO - add pushList, popList, peekList
 * Created by andrej on 18.6.2017.
 *
 *
 */


package BinaryHeap


class BinaryHeap<T>(private var comp: (T, T) -> Boolean) {

    private var _data: MutableList<T> = mutableListOf<T>()

    val size: Int
        get() = _data.size

    fun isEmpty(): Boolean = _data.isEmpty()

    /**
     * adds element to BinaryHeap and reorder _data to maintain heap consistency
     */
    fun push(x: T) {
        _data.add(x)
        var index: Int = size - 1

        while (index > 0) {
            val parent = parentIndex(index)
            if(comp(_data[index], _data[parent])) {
                swapAt(index, parent)
                index = parent
            } else {
                break
            }
        }
    }


    /**
     * remove "smallest" element from BinaryHeap and reorder _data to maintain heap consistency
     */
    fun pop(): T? {
        if (isEmpty()) return null

        val ret = _data[0]
        var index = 0

        swapAt(0, size - 1)
        _data = _data.dropLast(1).toMutableList()

        while (index < size) {
            val childIndex = minChildIndex(index)
            if (childIndex != null && comp(_data[childIndex], _data[index])) {
                swapAt(index, childIndex)
                index = childIndex
            } else {
                break
            }
        }
        return ret
    }


    /**
     * shows last element of heap without removal
     */
    fun peek(): T? = if (!isEmpty()) _data[0] else null


    /**
     * helper functions for internal book-keeping
     */

    private fun parentIndex(n: Int) = (n + 1) / 2 - 1

    private fun minChildIndex(n: Int): Int? {
        val childR = (n + 1) * 2
        val childL = childR - 1

        if (childL >= size) return null
        else if (childR < size && comp(_data[childR], _data[childL])) return childR
        else return childL
    }

    private fun swapAt(n1: Int, n2: Int) {
        val temp = _data[n1]
        _data[n1] = _data[n2]
        _data[n2] = temp
    }
}



/**
 * create BinaryHeap of Comparable types, heap will be sorted according to natural ordering of elements
 */
fun <T: Comparable<T>> binaryHeap(): BinaryHeap<T> {
    return BinaryHeap<T>({x: T, y: T -> x < y})
}

/**
 * create BinaryHeap of any kind of types, ordering will be done according to provided predicate function
 */
fun <T> binaryHeap(comp: (T, T) -> Boolean): BinaryHeap<T> {
    return BinaryHeap<T>(comp)
}

/**
 * create a BinaryHeap from data which has a natural order
 */
fun <T: Comparable<T>> toBinaryHeap(vararg args: T): BinaryHeap<T> {
    val ret = binaryHeap<T>()
    for (x in args) ret.push(x)
    return ret
}


/**
 * create a BinaryHeap from data that is ordered according to provided comparison function
 */
fun <T> toBinaryHeap(comp: (T, T) -> Boolean, vararg args: T): BinaryHeap<T> {
    val ret = BinaryHeap<T>(comp)
    for (x in args) ret.push(x)
    return ret
}

fun main(args: Array<String>) {
    val pq = binaryHeap<Int>({ x, y -> x > y })

    pq.push(2)
    pq.push(3)
    pq.push(4)
    pq.push(1)
    pq.push(1)
    pq.push(5)
    pq.push(12)
    pq.push(666)
    pq.push(-123)

    val pq2 = pq

    println(pq.peek())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())
    println(pq.pop())

    println()
    println(pq2.peek())
    println()

    val pq3 = toBinaryHeap(2,3,4,1,12,16,22,42,-1234,666,8)
    while (!pq3.isEmpty()) println(pq3.pop())

}
