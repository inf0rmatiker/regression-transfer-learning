import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;

public class Application {

    private static final Logger log = LogManager.getFormatterLogger(Application.class);

    public static void main(String[] programArgs) {

        SparkSession spark = SparkSession.builder()
                .appName("Linear Regression Transfer Learning Experiment")
                .getOrCreate();

        JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());

        KMeansClusteringImpl kMeansClustering = new KMeansClusteringImpl(5, 10);
        kMeansClustering.buildModel(sparkContext);
    }
}
