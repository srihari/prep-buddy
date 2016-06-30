package org.apache.datacommons.prepbuddy.rdds

import org.apache.datacommons.prepbuddy.types.CSV
import org.apache.log4j.{Level, Logger}
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Assert._
import org.scalatest.{BeforeAndAfterEach, FunSuite}


class TransformableRDDTest extends FunSuite with BeforeAndAfterEach {

  var sparkContext: SparkContext = null

  override def beforeEach() {
    val sparkConf: SparkConf = new SparkConf().setAppName(getClass.getName).setMaster("local")
    sparkContext = new SparkContext(sparkConf)
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
  }

  override def afterEach() {
    sparkContext.stop()
  }

  test("should be able to count on transformableRdd") {
    val data = Array("1,23", "2,45", "3,65", "4,67", "5,23")
    val dataSet: RDD[String] = sparkContext.parallelize(data)
    val transformableRDD: TransformableRDD = new TransformableRDD(dataSet, CSV)
    assert(5 == transformableRDD.count())
  }

  test("should deduplicate a dataset") {
    val records: Array[String] = Array("Smith,Male,USA,12345", "John,Male,USA,12343", "John,Male,USA,12343", "Smith,Male,USA,12342", "John,Male,India,12343", "Smith,Male,USA,12342")
    val initialDataset: JavaRDD[String] = sparkContext.parallelize(records)
    val initialRDD: TransformableRDD = new TransformableRDD(initialDataset)
    val deduplicatedRDD: TransformableRDD = initialRDD.deduplicate()
    assertEquals(4, deduplicatedRDD.count)
  }
}
