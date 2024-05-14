package benchmarks.builder

import com.ucasoft.kcron.Cron
import com.ucasoft.kcron.core.builders.DelicateIterableApi
import com.ucasoft.kcron.core.extensions.years
import kotlinx.benchmark.*

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@Warmup(iterations = 2)
@State(Scope.Benchmark)
open class Iterate {

    @Param("1", "10", "100", "1000", "10000")
    var take = 1

    private val builder = Cron.builder()

    @Setup
    fun prepare() {
        builder
            .years(2050..2055)
    }

    @OptIn(DelicateIterableApi::class)
    @Benchmark
    fun iterate(bh: Blackhole) {
        for (date in builder.asIterable().take(take)) {
            bh.consume(date)
        }
    }
}