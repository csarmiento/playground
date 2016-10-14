package amazon.interview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Assumptions:
 * 1. The set of stacks is generic
 * 2. The capacity threshold correspond to the maximum size of internal stacks
 * 3. The capacity threshold is user defined in class constructor
 * 4. SetOfStacks is not really a Set, since internal stacks could be equals (contain the same elements)
 * 5. SetOfStacks must have size and isEmtpy methods and must implement Iterable to beheve as a single stack
 */
public class SetOfStacks<T> implements Iterable<T> {
    // Threshold of capacity for each stack in the set
    private int capacityThreshold;
    // Current size of the stack
    private int size;

    List<Stack<T>> stacks;

    public SetOfStacks(int capacityThreshold) {
        this.capacityThreshold = capacityThreshold;
        this.stacks = new ArrayList<Stack<T>>();
        this.size = 0;
        stacks.add(new Stack<T>());
    }

    public T push(T elem) {
        Stack<T> stack = stacks.get(stacks.size() - 1);
        if (stack.size() >= capacityThreshold) {
            stacks.add(new Stack<T>());
            stack = stacks.get(stacks.size() - 1);
        }
        T retVal = stack.push(elem);
        size++;
        return retVal;
    }

    public T pop() {
        Stack<T> stack = stacks.get(stacks.size() - 1);
        T elem = stack.pop();
        if (stack.isEmpty()) {
            stacks.remove(stacks.size() - 1);
        }
        size--;
        return elem;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int stack = 0, posIntoStack = 0, currentPos = 0;

        @Override
        public boolean hasNext() {
            return currentPos < size;
        }

        @Override
        public T next() {
            T elem = stacks.get(stack).elementAt(posIntoStack);
            if (posIntoStack + 1 >= capacityThreshold) {
                posIntoStack = 0;
                stack++;
            } else {
                posIntoStack++;
            }
            currentPos++;
            return elem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
