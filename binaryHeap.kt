/**
 * BinaryHeap.kt
 * A binary heap data structure.
 * TODO - add pushArray, popArray, peekArray
 * Created by andrej on 18.6.2017.
 */

class BinaryHeap<T>(private var comp: (x: T, y: T) -> Boolean) {

    private var _data: MutableList<T> = mutableListOf<T>()
    val size: Int
        get() = _data.size

    fun isEmpty(): Boolean = _data.isEmpty()

    fun push(x: T) {
        _data.add(x)
        var index: Int = size - 1

        while (index > 0) {
            val parent = parentIndex(index)

            // swap when comparison true
            if(comp(_data[index], _data[parent])) {
                swapAt(index, parent)
                index = parent
            } else {
                break
            }
        }
    }

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

    fun peek(): T? = if (!isEmpty()) _data[0] else null

    private fun parentIndex(n: Int) = (n + 1) / 2 - 1

    private fun minChildIndex(n: Int): Int? {
        val childR = (n + 1) * 2
        val childL = childR - 1

        // null when no children exist, otherwise check if right child exists, return the index of max
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

fun <T: Comparable<T>> binaryHeap(): BinaryHeap<T> {
    return BinaryHeap<T>({x: T, y: T -> x < y})
}

fun <T> binaryHeap(comp: (x: T, y: T) -> Boolean): BinaryHeap<T> {
    return BinaryHeap<T>(comp)
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
    println(pq.pop())

}
