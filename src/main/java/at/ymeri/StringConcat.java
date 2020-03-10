package at.ymeri;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringConcat {

    @State(Scope.Benchmark)
    public static class CollectionSamples {
        @Param({ "10", "20", "50", "100", "200", "300", "500", "1000", "10000" })
        int length;

        public List<String> values;

        @Setup
        public void computeValues() {
            values = Stream
                    .iterate(1, i -> i + 1)
                    .limit(length)
                    .map(i -> Integer.toString(i))
                    .collect(Collectors.toList());
        }
    }

    @Benchmark
    @Fork(value = 2, warmups = 3)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringJoin(CollectionSamples samples) {
        String concatResult = String.join(",", samples.values);
    }

    @Benchmark
    @Fork(value = 2, warmups = 3)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingCollection(CollectionSamples samples) {
        String concatResult = samples.values.stream().collect(Collectors.joining(","));
    }


    @Benchmark
    @Fork(value = 2, warmups = 3)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingLoop(CollectionSamples samples) {
        String concatResult = "";
        for(String value : samples.values) {
            concatResult += ",";
        }
    }
}
