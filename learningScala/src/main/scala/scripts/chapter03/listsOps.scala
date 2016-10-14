// The empty list
List()
Nil

// Creates a new List[String] with the three values: "Cool", "tools", "rule"
List("Cool", "tools", "rule")

// Creates a new List[String] with the three values "Will", "fill", and "until"
val thrill = "Will" :: "fill" :: "until" :: Nil
println(thrill)

// Concatenates two lists (returns a new List[String] with values "a", "b", "c", and "d")
val list = List("a", "b") ::: List("c", "d")
println(list)
val list1 = "e" :: list
println(list1)

// Returns the element at index 2 (zero based) of the thrill list (returns "until")
println(thrill(2))

// Counts the number of string elements in thrill that have length 4 (returns 2)
val count: Int = thrill.count(s => s.length == 4)
println(count)

// Returns the thrill list without its first 2 elements (returns List("until"))
var other = thrill.drop(2)
println(other)

// Returns the thrill list without its rightmost 2 elements (returns List("Will"))
other = thrill.dropRight(2)
println(other)

// Determines whether a string element exists in thrill that has the value "until" (returns true)
var b = thrill.exists(s => s == "until")
println(b)

// Returns a list of all elements, in order, of the thrill list that have length 4 (returns List("Will", "fill"))
val filter: List[String] = thrill.filter(s => s.length == 4)
println(filter)

// Indicates whether all elements in the thrill list end with the letter "l" (returns true)
val forall = thrill.forall(s => s.endsWith("l"))
println(forall)

// Executes the print statement on each of the strings in the thrill list (prints "Willfilluntil")
thrill.foreach(s => print(s))
println

// Same as the previous, but more concise (also prints "Willfilluntil")
thrill.foreach(print)
println

// Returns the first element in the thrill list (returns "Will")
val head = thrill.head
println(head)

// Returns a list of all but the last element in the thrill list (returns List("Will", "fill"))
val init = thrill.init
println(init)

// Indicates whether the thrill list is empty (returns false)
val empty = thrill.isEmpty
println(empty)

// Returns the last element in the thrill list (returns "until")
val last = thrill.last
println(last)

// Returns the number of elements in the thrill list (returns 3)
val length = thrill.length
println(length)

// Returns a list resulting from adding a "y" to each string element in the thrill list
// (returns List("Willy", "filly", "untily"))
var map = thrill.map(s => s + "y")
println(map)

// Returns a list resulting from converting to upper case the first char of each string element in the thrill list
// (returns List("Will", "Fill", "Until"))
map = thrill.map(s => s.charAt(0).toUpper + s.substring(1))
println(map)

// Makes a string with the elements of the list (returns "Will, fill, until")
val string = thrill.mkString(", ")
println(string)

// Returns a list of all elements, in order, of the thrill list except those that have length 4
// (returns List("until"))
val filterNot = thrill.filterNot(s => s.length == 4)
println(filterNot)

// Returns a list containing all elements of the thrill list in reverse order
// (returns List("until", "fill", "Will"))
val reverse = thrill.reverse
println(reverse)

// Returns a list containing all elements of the thrill list in alphabetical order of the first character lowercased
// (returns List("fill", "until", "Will"))
val sortWith = thrill.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)
println(sortWith)

// Returns the thrill list minus its first element (returns List("fill", "until"))
val tail = thrill.tail
println(tail)