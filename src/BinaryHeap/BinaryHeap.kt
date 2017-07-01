/**
 * BinaryHeap.kt
 * A binary heap data structure.
 *
 * use binaryHeap<T>() to create an empty BinaryHeap
 * use binaryHeap<T>({ x, y -> x > y}) to create an empty BinaryHeap with a predicate (if T is Comparable the lambda
 *  sorts the elements in reverse order)
 * use toBinaryHeap(3, 1, 15, 2, 4, 42) to create a BinaryHeap<Int> from the given Int elements
 * use toBinaryHeap({ x, y -> x > y}, 3, 1, 15, 2, 4, 42) to create a BinaryHeap<Int> with a predicate
 *
 * use bh.push(x) to add x to the BinaryHeap bh
 * use bh.pop() to get the top element from BinaryHeap bh
 * use bh.peek() to get the top element from BinaryHeap bh without removing it
 *
 * use bh.size to get the number of elements in BinaryHeap bh
 * use bh.isEmpty() to test whether BinaryHeap bh is empty
 * Note that if you attempt to use pop on an empty BinaryHeap IndexOutOfBoundsException will be thrown!
 *
 * Note: for (i in bh) will iterate through BinaryHeap bh while emptying it. After iterating through it bh will be empty
 *
 *
 * Created by andrej on 18.6.2017.
 * Updated 30.06.2017
 */


package BinaryHeap


/**
 * MutableIterator for the BinaryHeap class. Returned by BinaryHeap.iterator() function
 * Warning: iteration removes elements from the binary heap!
 */
private class BHMutableIterator<out T>(private val _bh: BinaryHeap<T>) : MutableIterator<T> {
    override fun remove() { _bh.pop() }

    override fun next(): T = _bh.pop()

    override fun hasNext(): Boolean = !_bh.isEmpty()
}


/**
 * a generic BinaryHeap that compares elements according to predicate comp
 */
class BinaryHeap<T>(private var comp: (T, T) -> Boolean) : MutableIterable<T>  {

    private var _data: ArrayList<T> = ArrayList()   // data member
    private val _iter = BHMutableIterator<T>(this)  // a MutableIterator


    val size: Int
        get() = _data.size

    fun isEmpty(): Boolean = _data.isEmpty()

    override fun iterator(): MutableIterator<T> = _iter


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
    fun pop(): T {
        if (isEmpty()) throw IndexOutOfBoundsException("Attempted to pop element from empty BinaryHeap")

        val ret = _data[0]
        var index = 0

        swapAt(0, size - 1)
        _data.removeAt(size - 1)

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
    fun peek(): T {
        if (isEmpty()) throw IndexOutOfBoundsException("Attempted to pop element from empty BinaryHeap")
        return _data[0]
    }


    /**
     * changes the predicate function so that ordering is now done using the new one. The function reorders the current
     * elements in the BinaryHeap according to the new function.
     */
    fun predicate(pred: (T, T) -> Boolean) {
        // TODO
        // perhaps add as external helper functions to just copy a BinaryHeap?
    }


    /**
     * pops elements off the heap while test is true where test is a lambda/function reference or until heap exhausted
     */
    fun popWhile(test: (T) -> Boolean): ArrayList<T> {
        val ret = ArrayList<T>()
        while (!isEmpty() && test(peek())) {
            ret.add(pop())
        }
        return ret
    }

    /**
     * as above, except it will stop when test condition is met (or heap exhausted)
     */
    fun popUntil(test: (T) -> Boolean): ArrayList<T> {
        val ret = ArrayList<T>()
        while (!isEmpty() && !test(peek())) {
            ret.add(pop())
        }
        return ret
    }


    /**
     *  replaces the predicate function comp with pred and reorders BinaryHeap using the new comp
     *  At large sizes potentially problematic, the space is doubled
     *  TODO: do we need this? Is it better than non-trivial constructor that simply pops from old, pushes to new?
     */
    fun changePredicate(pred: (T, T) -> Boolean) {
        val temp = _data
        _data = ArrayList<T>(initialCapacity = _data.size)
        comp = pred
        for (i in temp) {
            push(i)
        }
    }


    /**
     * Clones the binary heap
     * TODO: not sure about this one, although I'm sure it works unsafe cast makes me nervous :) Also, do we need it?
     */
    fun clone(): BinaryHeap<T> {
        val ret = BinaryHeap<T>(comp)
        ret._data = _data.clone() as ArrayList<T>
        return ret
    }


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


/**
 * create a new BinaryHeap from old one that gets emptied, using natural order of sorting for comparable
 */
fun <T: Comparable<T>> fromBinaryHeap(other: BinaryHeap<T>): BinaryHeap<T> {
    val ret = BinaryHeap<T>({x: T, y: T -> x < y})
    for (i in other) {
        ret.push(i)
    }
    return ret
}

/**
 * create a new BinaryHeap from old one that gets emptied, using the comp comparison function (T, T) -> Boolean
 */
fun <T> fromBinaryHeap(other: BinaryHeap<T>, comp: (T, T) -> Boolean): BinaryHeap<T> {
    val ret = BinaryHeap<T>(comp)
    for (i in other) {
        ret.push(i)
    }
    return ret
}
