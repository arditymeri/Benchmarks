package at.ymeri;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Fork(value = 1, warmups = 1)
@Measurement(iterations = 3)
@Warmup(iterations = 3)
public class StringConcat {

    @State(Scope.Benchmark)
    public static class CollectionSamples {

//        @Param({"2", "5", "10", "20", "30", "50", "90", "160", "290", "520", "940", "1690", "3050",
//                "5480", "9870", "17770", "31980"})
        @Param({"2", "5", "10", "20", "30", "50", "90"})
        int length;

        public List<String> values;
        public String result = "";

        @Setup
        public void computeValues() {
            result = "";
            values = Stream
                    .iterate(1, i -> i + 1)
                    .limit(length)
                    .map(i -> Integer.toString(i))
                    .collect(Collectors.toList());
        }

        @TearDown
        public void tearDown() {
            System.out.println("Concatenation length: " + result.length());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringJoin(CollectionSamples samples) {
        samples.result = String.join(",", samples.values);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingCollection(CollectionSamples samples) {
        samples.result = samples.values.stream().collect(Collectors.joining(","));
    }


    //@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingLoop(CollectionSamples samples) {
        String concatResult = "";
        for (String value : samples.values) {
            concatResult += ",";
        }
        samples.result = concatResult;
    }

    //@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringBuilder(CollectionSamples samples) {
        StringBuilder sb = new StringBuilder();

        for (String value : samples.values) {
            sb.append(value);
            sb.append(",");
        }
        samples.result = sb.toString();
    }
}
