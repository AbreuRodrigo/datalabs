package com.landcraft.server.services;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.landcraft.server.enums.ResponseCode;
import com.landcraft.server.utils.AccessValidator;
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
	public HashMap<String, Object> saveDocument(HashMap<String, Object> document) throws Exception {

		HashMap<String, Object> response = null;

		try {
			validator.validate(headers);

			if (mongoDB != null && document != null) {
				mongoDB.save("PlayerStats", document);
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