package com.bachelor;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bachelor.model.Image;
import com.bachelor.service.ImgService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerTest {
	@MockBean
	private ImgService service;
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /Image/1 - Found")
	void testGetImageIdFound() throws Exception {
		// Setup our mocked service
		Image mockImage = new Image(1, "mockPath", "Normal", 1);
		doReturn(Optional.of(mockImage)).when(service).getImageById(1);

		// Execute the GET request
		mockMvc.perform(get("/bachelor/image/getImage/{id}", 1).accept(MediaType.APPLICATION_JSON))

				// validate response code
				.andExpect(status().isOk()).andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))

				// Validate the response code and content type
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.physicalPath", is("mockPath"))).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.status", is("Normal")));

	}

	@Test
	@DisplayName("GET /Image/1 - NOT Found")
	void testGetImageIdNotFound() throws Exception {
		// Setup our mocked service
		doReturn(Optional.empty()).when(service).getImageById(1);
		// Execute the GET request
		mockMvc.perform(get("/bachelor/image/getImage/{id}", 1).accept(MediaType.APPLICATION_JSON))
				// Validate the response code and content type
				.andExpect(status().isNotFound());

	}

	@Test
	@DisplayName("POST /Image - Success")
	void testSubmitImage() throws Exception {

		// Setup mocked service
		Image postImage = new Image("mockPath", "Normal");
		Image mockImage = new Image(1, "mockPath", "Normal", 1);
		doReturn(mockImage).when(service).saubmitImage(any());

		mockMvc.perform(post("/bachelor/image/saubmitImage").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(postImage)))

				// Validate the response code and content type
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the headers
				.andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.physicalPath", is("mockPath")))
				.andExpect(jsonPath("$.status", is("Normal"))).andExpect(jsonPath("$.version", is(1)));
	}

//	
//	 @Test
//	    @DisplayName("PUT /product/1 - Version Mismatch")
//	    void testProductPutVersionMismatch() throws Exception {
//	        // Setup mocked service
//	        Product putProduct = new Product("Product Name", 10);
//	        Product mockProduct = new Product(1, "Product Name", 10, 2);
//	        doReturn(Optional.of(mockProduct)).when(service).findById(1);
//	        doReturn(true).when(service).update(any());
//
//	        mockMvc.perform(put("/product/{id}", 1)
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .header(HttpHeaders.IF_MATCH, 1)
//	                .content(asJsonString(putProduct)))
//
//	                // Validate the response code and content type
//	                .andExpect(status().isConflict());
//	    }

//	    @Test
//	    @DisplayName("PUT /product/1 - Not Found")
//	    void testProductPutNotFound() throws Exception {
//	        // Setup mocked service
//	        Product putProduct = new Product("Product Name", 10);
//	        doReturn(Optional.empty()).when(service).findById(1);
//
//	        mockMvc.perform(put("/product/{id}", 1)
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .header(HttpHeaders.IF_MATCH, 1)
//	                .content(asJsonString(putProduct)))
//
//	                // Validate the response code and content type
//	                .andExpect(status().isNotFound());
//	    }
//
//	    @Test
//	    @DisplayName("DELETE /product/1 - Success")
//	    void testProductDeleteSuccess() throws Exception {
//	        // Setup mocked product
//	        Product mockProduct = new Product(1, "Product Name", 10, 1);
//
//	        // Setup the mocked service
//	        doReturn(Optional.of(mockProduct)).when(service).findById(1);
//	        doReturn(true).when(service).delete(1);
//
//	        // Execute our DELETE request
//	        mockMvc.perform(delete("/product/{id}", 1))
//	                .andExpect(status().isOk());
//	    }
//	
//	
//	
	
	
	
	
	@Test
	@DisplayName("GET /getAllImage")
	void testGetAllImage() throws Exception {

		// Setup mocked service
		ArrayList<Image> mockImage = new ArrayList<Image>();
		mockImage.add(new Image(1, "mockPath", "Normal", 1));

		doReturn(mockImage).when(service).getAllImages();
		mockMvc.perform(get("/bachelor/image/getAllImage"))
				.andExpect(status().isFound());

	}
	
	
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
