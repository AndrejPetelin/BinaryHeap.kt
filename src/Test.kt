/**
 * General tests for usage and functionality of BinaryHeap
 *
 * Created by irenenaya on 6/30/17.
 */

import BinaryHeap.*

fun main (vararg argv : String) {
    // Creating priority queue
    var pq = toBinaryHeap(1.4, 5.5, 2.9, 3.8, 10.0, 15.2, 3.2, 4.8)

    println("Peeking: ${pq.peek()}")
    println("Popping now should give the same value: ${pq.pop()}")

    println("Printing queue after popping first element")
    for (i in pq) println(i)

    val list = mutableListOf<Double>()
    for (i in 0..50) list.add(Math.random() * 10)

    // passing a MutableList as vararg and a predicate to change sorting order
    pq = toBinaryHeap(comp={x : Double, y : Double ->  x > y}, args= *list.toTypedArray() )

    println("Randomized reverse sorted: ")
    for (i in pq) println(i)
}