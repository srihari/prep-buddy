package specs

import org.apache.datacommons.prepbuddy.qualityanalyzers.{DataType, MOBILE_NUMBER}
import org.apache.datacommons.prepbuddy.rdds.TransformableRDD
import org.apache.spark.rdd.RDD

object SampleTest extends FunctionalTestRunner {
    test("should infer the value type to be mobile number at the specified column") {
        val testableRDD: RDD[String] = sc.textFile("data/calls.csv")
        val callRecords: TransformableRDD = new TransformableRDD(testableRDD)

        val actualType: DataType = callRecords.inferType(0)

        assert(MOBILE_NUMBER == actualType)
    }

    test("should deduplicate the given rdd") {
        val testableRDD: RDD[String] = sc.textFile("data/calls.csv")
        val callRecords: TransformableRDD = new TransformableRDD(testableRDD)

        val deduplicateRDD: TransformableRDD = callRecords.deduplicate(0 :: Nil)
        assert(deduplicateRDD.count() == 27)
    }

    test("should fail because of wrong assertion value") {
        val testableRDD: RDD[String] = sc.textFile("data/calls.csv")
        val callRecords: TransformableRDD = new TransformableRDD(testableRDD)

        assert(1 == callRecords.count)
    }

    printReport()
}
