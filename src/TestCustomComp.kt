/**
 *
 * Testing BinaryHeap with Comparable custom objects
 *
 * Created by irenenaya on 6/30/17.
 */


import BinaryHeap.*

data class PersonComp (var name : String, var age : Int) : Comparable<PersonComp> {
    override operator fun compareTo(other: PersonComp): Int = this.age.compareTo(other.age)
}


fun main (vararg argv : String) {

    val pq = binaryHeapOf(PersonComp("John", 30), PersonComp("Mary", 23), PersonComp("Paul", 40))

    println("Youngest Person is: ${pq.peek().name}, who is ${pq.peek().age} years old")

    pq.push(PersonComp("Peter", 15))

    println("Youngest now is: ${pq.peek().name}, who is ${pq.peek().age} years old")

    println("Printing whole queue:")
    for (i in pq) println("${i.name} is ${i.age} years old")

}
