package edu.mssm.pharm.maayanlab.Harmonizome.dal;

import edu.mssm.pharm.maayanlab.Harmonizome.model.Dataset;
import edu.mssm.pharm.maayanlab.Harmonizome.model.DatasetPairVisualization;
import edu.mssm.pharm.maayanlab.Harmonizome.model.DatasetVisualization;
import edu.mssm.pharm.maayanlab.common.database.HibernateUtil;

import java.util.List;

public class HeatMapDao {

    public static DatasetPairVisualization getDatasetPairVisualization(String dataset_1, String dataset_2) {
        return (DatasetPairVisualization) HibernateUtil.getCurrentSession()
            .createQuery(
                "SELECT dpv FROM DatasetPairVisualization AS dpv " +
                "  JOIN dpv.dataset1 AS ds1 " +
                "  JOIN dpv.dataset2 AS ds2 " +
                "WHERE ds1.name = :dataset_1 AND ds2.name = :dataset_2"
            )
            .setString("dataset_1", dataset_1)
            .setString("dataset_2", dataset_2)
            .uniqueResult();
    }

    public static DatasetVisualization getDatasetVisualization(String dataset, String type) {
        return (DatasetVisualization) HibernateUtil.getCurrentSession()
            .createQuery(
                "SELECT dv FROM DatasetVisualization AS dv " +
                "  JOIN dv.dataset AS dataset " +
                "WHERE dataset.name = :dataset AND dv.type = :type"
            )
            .setString("dataset", dataset)
            .setString("type", type)
            .uniqueResult();
    }

    @SuppressWarnings("unchecked")
	public static List<Dataset> getDatasetsFromType(String type) {
        return (List<Dataset>) HibernateUtil.getCurrentSession()
            .createQuery(
                "SELECT dataset FROM Dataset AS dataset " +
                "  JOIN dataset.datasetVisualizations AS dsv " +
                "WHERE dsv.type = :type"
            )
            .setString("type", type)
            .list();
    }

    @SuppressWarnings("unchecked")
	public static List<String> getLeftDatasetInPair() {
        return (List<String>) HibernateUtil.getCurrentSession()
            .createSQLQuery(
                "SELECT DISTINCT name FROM dataset " +
                "  JOIN dataset_pair_visualization ON " +
                "       dataset_pair_visualization.dataset_1_fk = dataset.id "
            )
            .list();
    }

    @SuppressWarnings("unchecked")
	public static List<String> getRightDatasetInPair(String dataset1) {
        String subQuery = String.format("(SELECT id FROM dataset WHERE name = \"%s\")", dataset1);
        String query = String.format(
            "SELECT DISTINCT name FROM dataset " +
            "  JOIN dataset_pair_visualization ON dataset_pair_visualization.dataset_2_fk = dataset.id " +
            "WHERE dataset_pair_visualization.dataset_1_fk = %s",
            subQuery
        );
        System.out.println(query);
        return (List<String>) HibernateUtil.getCurrentSession()
            .createSQLQuery(query)
            .list();
    }
}