package org.sustain;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class Application {

    public static void main(String[] programArgs) {
        SparkSession spark = SparkSession.builder()
                .appName("Linear Regression Transfer Learning Experiment")
                .master("spark://lattice-150:8079")
                .getOrCreate();

        JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());

        org.sustain.KMeansClusteringImpl kMeansClustering = new org.sustain.KMeansClusteringImpl(5, 10);
        kMeansClustering.buildModel(sparkContext);


    }
}
