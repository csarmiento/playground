// Perhaps the most common operator you’ll use with lists is '::', which
// is pronounced "cons." Cons prepends a new element to the beginning of an
//  existing list, and returns the resulting list. For example, if you run this script:

val twoThree = List(2, 3)
val oneTwoThree = 1 :: twoThree
println(oneTwoThree)
