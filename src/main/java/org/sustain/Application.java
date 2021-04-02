package org.sustain;
/*
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
*/
public class Application {

    //public static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] programArgs) {

        /*
        SparkSession spark = SparkSession.builder()
                .appName("Linear Regression Transfer Learning Experiment")
                .master("spark://lattice-150:8079")
                .getOrCreate();

        JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());

        org.sustain.KMeansClusteringImpl kMeansClustering = new org.sustain.KMeansClusteringImpl(5, 10);
        kMeansClustering.buildModel(sparkContext);

         */
        System.out.println("GOT HERE");
    }
}
