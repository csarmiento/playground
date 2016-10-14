package amazon.interview;

import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

public class SetOfStacksTest {

    private int[] test1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    @Test
    public void testPush() throws Exception {
        SetOfStacks<Integer> setOfStacks = new SetOfStacks<Integer>(1);
        for (int num : test1) {
            setOfStacks.push(num);
        }
        printSetOfStacks(setOfStacks);

    }

    private void printSetOfStacks(SetOfStacks<Integer> setOfStacks) {
        for (Integer i : setOfStacks) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    @Test
    public void testPop() throws Exception {
        SetOfStacks<Integer> setOfStacks = new SetOfStacks<Integer>(1);
        for (int num : test1) {
            setOfStacks.push(num);
        }

        int originalSize = setOfStacks.size();
        System.out.println(originalSize);

        for (int i = 0; i < originalSize; i++) {
            System.out.print("(" + setOfStacks.pop() + ") -> ");
            printSetOfStacks(setOfStacks);
        }
    }


    @Test
    public void testSingleStack() {
        Stack<Integer> s = new Stack<Integer>();
        for (int num : test1) {
            s.push(num);
        }
        printStack(s);
        s.pop();
        printStack(s);
    }

    @Test
    public void testSingleStackPop() {
        Stack<Integer> s = new Stack<Integer>();
        for (int num : test1) {
            s.push(num);
        }
        for (Integer i : s) {
            System.out.print("(" + s.pop() + ") -> ");
            printStack(s);
        }
    }

    private void printStack(Stack<Integer> s) {
        System.out.println(Arrays.toString(s.toArray()));
    }
}