package com.bachelor;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.any;

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
		Image mockImage = new Image(1, "mockPath", "Normal",1);
		doReturn(Optional.of(mockImage)).when(service).getImageById(1);

		// Execute the GET request
		mockMvc.perform(get("/bachelor/image/getImage/{id}", 1).accept(MediaType.APPLICATION_JSON))

				// validate response code
				.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
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
			Image postImage = new Image( "mockPath", "Normal");
			Image mockImage = new Image(1, "mockPath", "Normal",1);
	        doReturn(mockImage).when(service).saubmitImage(any());
	        
	        mockMvc.perform(post("/bachelor/image/saubmitImage")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(postImage)))

	                // Validate the response code and content type
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

	                // Validate the headers
	                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
	                .andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))

	                // Validate the returned fields
	                .andExpect(jsonPath("$.id", is(1)))
	                .andExpect(jsonPath("$.physicalPath", is("mockPath")))
	                .andExpect(jsonPath("$.status", is("Normal")))
	                .andExpect(jsonPath("$.version", is(1)));
	    }


	 static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
