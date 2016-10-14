package amazon.interview;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoxTest {
    private List<Box> getBoxesSet1() {
        List<Box> boxes = new ArrayList<Box>();

        boxes.add(new Box(10, 5));
        boxes.add(new Box(10, 5));
        boxes.add(new Box(20, 2));
        boxes.add(new Box(5, 5));
        boxes.add(new Box(5, 5));
        boxes.add(new Box(7, 10));
        boxes.add(new Box(10, 7));
        boxes.add(new Box(10, 3));
        boxes.add(new Box(1, 1));
        boxes.add(new Box(3, 3));
        boxes.add(new Box(2, 2));
        boxes.add(new Box(1, 100));
        return boxes;
    }

    private List<Box> getBoxesSet2() {
        List<Box> boxes = new ArrayList<Box>();

        boxes.add(new Box(65, 100));
        boxes.add(new Box(70, 150));
        boxes.add(new Box(56, 90));
        boxes.add(new Box(75, 190));
        boxes.add(new Box(60, 95));
        boxes.add(new Box(68, 110));
        return boxes;
    }

    @Test
    public void testSorting() {
        List<Box> boxes1 = getBoxesSet1();

        printBoxes(boxes1);
        Collections.sort(boxes1, Box.DESCENDING_HEIGHT_WEIGHT_COMPARATOR);
        printBoxes(boxes1);

        List<Box> boxes2 = getBoxesSet2();

        printBoxes(boxes2);
        Collections.sort(boxes2, Box.DESCENDING_HEIGHT_WEIGHT_COMPARATOR);
        printBoxes(boxes2);
    }

    @Test
    public void testLargestTower() {
        List<Box> boxes = getBoxesSet1();
        int towerHeight = Box.largestTower(boxes);
        Assert.assertEquals(5, towerHeight);

        boxes = getBoxesSet2();
        towerHeight = Box.largestTower(boxes);
        Assert.assertEquals(6, towerHeight);

    }

    private void printBoxes(List<Box> boxes) {
        Box[] boxesArr = boxes.toArray(new Box[boxes.size()]);
        System.out.println(Arrays.toString(boxesArr));
    }
}