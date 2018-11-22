package org.cocome.reportsservice.util;

import org.apache.log4j.Logger;

import javax.ws.rs.NotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This class intercepts any NotFoundExceptions and adds the proper error
 * message to the response body. <br>
 * This is necessary as a NotFoundException is thrown during REST-Call and
 * therefore serialized and sent to another servers. <br>
 * When the requesting server catches the error, it cannot simply get the
 * message by e.getMessage(). <br>
 * It has to read it from the entity given in the Response.
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Provider
public class ReportsserviceExceptionMapper implements ExceptionMapper<NotFoundException> {

	private final Logger LOG = Logger.getLogger(ReportsserviceExceptionMapper.class);

	@Override
	public Response toResponse(NotFoundException exception) {
		LOG.debug("ExceptionMapper: Map exception ");

		int exceptionStatusCode = exception.getResponse().getStatus();
		String exceptionMessage = exception.getMessage();

		if (exceptionMessage == null) {
			Response.Status exceptionStatus = Response.Status.fromStatusCode(exceptionStatusCode);
			exceptionMessage = (exceptionStatus != null) ? exceptionStatus.getReasonPhrase()
					: String.format("Exception has been thrown with Status Code : %s", exceptionStatusCode);
		}

		return Response.status(exceptionStatusCode).entity(exceptionMessage).type(MediaType.TEXT_PLAIN).build();
	}
}