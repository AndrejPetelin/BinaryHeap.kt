/**
 *
 * Testing BinaryHeap with non Comparable objects using a predicate
 *
 * Created by irenenaya on 6/30/17.
 */



import BinaryHeap

data class Person(var name : String, var age : Int)


fun main (vararg argv : String) {

    val pq = binaryHeap<Person>( { x, y -> x.age < y.age})

    pq.push(Person("John", 30))
    pq.push(Person("Mary", 23 ))
    pq.push(Person("Paul", 40))

    for (i in pq) println("Person: ${i.name}, is ${i.age}")

}

