package edu.mssm.pharm.maayanlab.Harmonizome.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import edu.mssm.pharm.maayanlab.Harmonizome.dal.GenericDAO;
import edu.mssm.pharm.maayanlab.Harmonizome.model.BioEntityMetadata;
import edu.mssm.pharm.maayanlab.Harmonizome.net.UrlUtil;
import edu.mssm.pharm.maayanlab.Harmonizome.util.Constant;
import edu.mssm.pharm.maayanlab.common.database.HibernateUtil;

public class BioEntityPage {

	public static <T> void doGet(HttpServletRequest request, HttpServletResponse response, Class<T> klass) throws ServletException, IOException {
		String query = UrlUtil.getPath(request);
		T bioEntity = null;
		try {
			HibernateUtil.beginTransaction();
			bioEntity = GenericDAO.getBioEntityFromKeyColumn(klass, query);
			HibernateUtil.commitTransaction();
		} catch (HibernateException he) {
			HibernateUtil.rollbackTransaction();
		}
					
		if (bioEntity == null) {
			request.setAttribute("query", query);
			request.getRequestDispatcher(Constant.TEMPLATE_DIR + "404.jsp").forward(request, response);				
		} else {
			BioEntityMetadata anno = klass.getAnnotation(BioEntityMetadata.class);
			request.setAttribute(anno.name(), bioEntity);
			request.getRequestDispatcher(Constant.TEMPLATE_DIR + anno.jsp()).forward(request, response);
		}
	}
}