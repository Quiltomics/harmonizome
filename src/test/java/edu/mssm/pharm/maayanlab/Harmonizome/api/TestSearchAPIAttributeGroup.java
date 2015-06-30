package edu.mssm.pharm.maayanlab.Harmonizome.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.mssm.pharm.maayanlab.Harmonizome.model.AttributeGroup;
import edu.mssm.pharm.maayanlab.Harmonizome.model.Dataset;
import edu.mssm.pharm.maayanlab.Harmonizome.model.Gene;
import edu.mssm.pharm.maayanlab.Harmonizome.pojo.JsonSchema;
import edu.mssm.pharm.maayanlab.Harmonizome.serdes.DatasetDeserializer;
import edu.mssm.pharm.maayanlab.Harmonizome.serdes.GeneSimpleDeserializer;

public class TestSearchAPIAttributeGroup extends Mockito {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter output;
	private PrintWriter writer;
	private Gson gson;

	@Before
	public void setup() throws IOException {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		output = new StringWriter();
		writer = new PrintWriter(output);
		when(response.getWriter()).thenReturn(writer);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Dataset.class, new DatasetDeserializer());
		gsonBuilder.registerTypeAdapter(Gene.class, new GeneSimpleDeserializer());
		gson = gsonBuilder.create();
	}

	@Test
	public void testByDataset() throws IOException, ServletException {
		when(request.getParameter("dataset")).thenReturn("Guide_to_Pharmacology_Protein_Ligands_of_Receptors"); 
		new SearchAPI().doGet(request, response);
		writer.flush();
		String json = output.toString();
		JsonSchema jsonSchema = gson.fromJson(json, JsonSchema.class);
		List<AttributeGroup> attributeGroups = jsonSchema.getAttributeGroup();
		assertEquals(attributeGroups.size(), 1);
		assertEquals(attributeGroups.get(0).getName(), "gene");
	}
	
	@Test
	public void testByDatasetGroup() throws IOException, ServletException {
		when(request.getParameter("datasetGroup")).thenReturn("physical_interactions"); 
		new SearchAPI().doGet(request, response);
		writer.flush();
		String json = output.toString();
		JsonSchema jsonSchema = gson.fromJson(json, JsonSchema.class);
		List<AttributeGroup> attributeGroups = jsonSchema.getAttributeGroup();
		List<String> validAttribueGroupNames = new ArrayList<String>();
		validAttribueGroupNames.add("gene");
		validAttribueGroupNames.add("chemical");
		for (AttributeGroup ag : attributeGroups) {
			assertTrue(validAttribueGroupNames.contains(ag.getName()));
		}
	}

	@Test
	public void testByDatasetType() throws IOException, ServletException {
		when(request.getParameter("datasetType")).thenReturn("protein_expression_immunohistochemistry"); 
		new SearchAPI().doGet(request, response);
		writer.flush();
		String json = output.toString();
		JsonSchema jsonSchema = gson.fromJson(json, JsonSchema.class);
		List<AttributeGroup> attributeGroups = jsonSchema.getAttributeGroup();
		assertEquals(attributeGroups.size(), 1);
		assertEquals(attributeGroups.get(0).getName(), "cell or tissue");
	}

	@Test
	public void testByAttributeGroup() throws ServletException, IOException {
		when(request.getParameter("attributeGroup")).thenReturn("sequence_feature"); 
		new SearchAPI().doGet(request, response);
		writer.flush();
		String json = output.toString();
		JsonSchema jsonSchema = gson.fromJson(json, JsonSchema.class);
		List<AttributeGroup> attributeGroups = jsonSchema.getAttributeGroup();
		assertEquals(attributeGroups.size(), 1);
		assertEquals(attributeGroups.get(0).getName(), "sequence feature");
	}

	@Test
	public void testByAttributeType() throws ServletException, IOException {
		when(request.getParameter("attributeType")).thenReturn("hub"); 
		new SearchAPI().doGet(request, response);
		writer.flush();
		String json = output.toString();
		JsonSchema jsonSchema = gson.fromJson(json, JsonSchema.class);
		List<AttributeGroup> attributeGroups = jsonSchema.getAttributeGroup();
		assertEquals(attributeGroups.size(), 1);
		assertEquals(attributeGroups.get(0).getName(), "gene");
	}
}