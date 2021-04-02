package org.sustain;

import com.mongodb.spark.config.ReadConfig;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.feature.MinMaxScaler;
import org.apache.spark.ml.feature.MinMaxScalerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import com.mongodb.spark.MongoSpark;
import scala.collection.JavaConverters;
import scala.collection.Seq;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KMeansClusteringImpl {

    private int kClusters, maxIterations;

    public KMeansClusteringImpl(int kClusters, int maxIterations) {
        this.kClusters = kClusters;
        this.maxIterations = maxIterations;
    }

    public Seq<String> convertListToSeq(List<String> inputList) {
        return JavaConverters.asScalaIteratorConverter(inputList.iterator()).asScala().toSeq();
    }

    public void buildModel(JavaSparkContext sparkContext) {

        // Initialize mongodb read configuration
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("spark.mongodb.input.collection", "county_stats");
        readOverrides.put("spark.mongodb.input.database", "sustaindb");
        readOverrides.put("spark.mongodb.input.uri", "mongodb://lattice-165:27018");
        ReadConfig readConfig = ReadConfig.create(sparkContext.getConf(), readOverrides);

        // Load mongodb rdd and convert to dataset
        System.out.println("Preprocessing data");
        Dataset<Row> collection = MongoSpark.load(sparkContext, readConfig).toDF();
        List<String> featuresList = new ArrayList<>(Arrays.asList("median_household_income"));
        Seq<String> features = convertListToSeq(featuresList);

        Dataset<Row> selectedFeatures = collection.select("GISJOIN", features);

        // Dropping rows with null values
        selectedFeatures = selectedFeatures.na().drop();

        // Assembling
        VectorAssembler assembler =
                new VectorAssembler().setInputCols(featuresList.toArray(new String[0])).setOutputCol("features");
        Dataset<Row> featureDF = assembler.transform(selectedFeatures);
        featureDF.show(10);

        // Scaling
        System.out.println("Normalizing features");
        MinMaxScaler scaler = new MinMaxScaler()
                .setInputCol("features")
                .setOutputCol("normalized_features");
        MinMaxScalerModel scalerModel = scaler.fit(featureDF);

        featureDF = scalerModel.transform(featureDF);
        featureDF = featureDF.drop("features");
        featureDF = featureDF.withColumnRenamed("normalized_features", "features");

        System.out.println("Dataframe after min-max normalization");
        featureDF.show(10);

        // KMeans Clustering
        KMeans kmeans = new KMeans().setK(this.kClusters).setSeed(1L);
        KMeansModel model = kmeans.fit(featureDF);

        Vector[] vectors = model.clusterCenters();

        System.out.println("======================== CLUSTER CENTERS =====================================");
        for (Vector vector : vectors) {
            System.out.println(vector.toString());
        }



    }

}
