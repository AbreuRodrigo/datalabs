package com.datalab.server.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.datalab.server.enums.ResponseCode;
import com.datalab.server.utils.AccessValidator;
import com.datalab.server.utils.MongoDB;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/document")
public class DocumentService {
	private final String DOCUMENT_NAME = "documentName";
	private final String CODE_ATTR = "code";
	private final String MESSAGE_ATTR = "message";

	private final MongoDB mongoDB;
	private final AccessValidator validator;
	private final HashMap<String, Object> failureResponse;

	@Context
	private HttpHeaders headers;

	public DocumentService() {
		mongoDB = new MongoDB();
		validator = new AccessValidator();
		failureResponse = buildSimpleFailureResponse();
	}

	@GET
	@Path("/{documentName}/{attributeName}/{attributeValue}")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, Object> getDocumentByAttribute(@PathParam("documentName") String documentName,
			@PathParam("attributeName") String attributeName, @PathParam("attributeValue") String attributeValue) {

		HashMap<String, Object> response = null;

		try {
			validator.validate(headers);

			HashMap<String, Object> query = new HashMap<String, Object>();
			query.put(attributeName, attributeValue);

			response = mongoDB.findWithCriterias(documentName, query);
		} catch (Exception e) {
			response = failureResponse;
		}

		return response;
	}
	
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<HashMap<String, Object>> listByDocument(HashMap<String,Object> params) {
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();
		
		try {
			validator.validate(headers);
			
			String documentName = (String) params.get(DOCUMENT_NAME);
			
			response = mongoDB.listByDocument(documentName);
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
				
		return response;
	}

	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, Object> findDocumentBy(HashMap<String, Object> criterias) {

		HashMap<String, Object> response = null;

		try {
			String documentName = (String) criterias.get(DOCUMENT_NAME);

			if (documentName != null) {
				criterias.remove(DOCUMENT_NAME);
			}

			validator.validate(headers);
			response = mongoDB.findWithCriterias(documentName, criterias);
		} catch (Exception e) {
			response = failureResponse;
		}

		return response;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, Object> saveDocument(String documentName, HashMap<String, Object> document) throws Exception {

		HashMap<String, Object> response = null;

		try {
			validator.validate(headers);

			if (mongoDB != null && document != null) {
				mongoDB.save(documentName, document);
			}

			response = buildCustomeSuccessResponse("Document saved successfully.");
		} catch (Exception e) {
			response = failureResponse;
		}

		return response;
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, Object> updateDocument(HashMap<String, Object> document) throws Exception {

		HashMap<String, Object> response = null;

		try {
			validator.validate(headers);

			if (mongoDB != null && document != null) {
				mongoDB.update("PlayerStats", document);
			}

			response = buildCustomeSuccessResponse("Document updated successfully.");
		} catch (Exception e) {
			response = failureResponse;
		}

		return response;
	}

	@POST
	@Path("/persist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, Object> persistDocument(HashMap<String, Object> document) throws Exception {

		HashMap<String, Object> response = null;
				
		try {
			validator.validate(headers);

			if (mongoDB != null && document != null) {
				mongoDB.persist("PlayerStats", document);
			}

			response = buildCustomeSuccessResponse("Document persisted successfully.");
		} catch (Exception e) {
			response = failureResponse;
		}

		return response;
	}

	private HashMap<String, Object> buildSimpleFailureResponse() {
		HashMap<String, Object> failure = new HashMap<String, Object>();
		failure.put(CODE_ATTR, ResponseCode.FAILURE.getCode());
		failure.put(MESSAGE_ATTR, "Request failed.");

		return failure;
	}
	
	private HashMap<String, Object> buildCustomeFailureResponse(String message) {
		HashMap<String, Object> failure = new HashMap<String, Object>();
		failure.put(CODE_ATTR, ResponseCode.FAILURE.getCode());
		failure.put(MESSAGE_ATTR, message);

		return failure;
	}
	
	private HashMap<String, Object> buildSimpleSuccessResponse() {
		HashMap<String, Object> success = new HashMap<String, Object>();
		success.put(CODE_ATTR, ResponseCode.SUCCESS.getCode());
		success.put(MESSAGE_ATTR, "Request success.");

		return success;
	}

	private HashMap<String, Object> buildCustomeSuccessResponse(String message) {
		HashMap<String, Object> success = new HashMap<String, Object>();
		success.put(CODE_ATTR, ResponseCode.SUCCESS.getCode());
		success.put(MESSAGE_ATTR, message);

		return success;
	}
}