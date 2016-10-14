package co.runtime.lambdas.tutorial;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * @author Camilo Sarmiento
 * @since 2016-10-13
 */
public class GenericProcessor {
    /**
     * Reconsider the method {@link PersonQueries#processPersonsWithFunction}. The following is a generic version of it
     * that accepts, as a parameter, a collection that contains elements of any data type:
     *
     * @param source
     * @param tester
     * @param mapper
     * @param block
     * @param <X>
     * @param <Y>
     */
    public static <X, Y> void processElements(Iterable<X> source, Predicate<X> tester, Function<X, Y> mapper, Consumer<Y> block) {
        for (X p : source) {
            if (tester.test(p)) {
                Y data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

    public static <X, Y> void processElementsWithLambdaExpressions
        (Iterable<X> source, Predicate<X> tester, Function<X, Y> mapper, Consumer<Y> block) {
        StreamSupport.stream(source.spliterator(), false).filter(tester).map(mapper).forEach(block);
    }
}
