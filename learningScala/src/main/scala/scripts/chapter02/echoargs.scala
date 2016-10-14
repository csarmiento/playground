// Although the examples in this section help explain while loops, they do
// not demonstrate the best Scala style. In the next section, you’ll see better
// approaches that avoid iterating through arrays with indexes.

var i = 0
while (i < args.length) {
  if (i != 0)
    print(" ")
  print(args(i))
  i += 1
}
println()