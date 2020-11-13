package com.bachelor.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bachelor.model.Image;
import com.bachelor.service.image.ImgService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
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
		Image mockImage = new Image(1, "mockPath", "Normal", 1);
		doReturn(Optional.of(mockImage)).when(service).getImageById(1);

		mockMvc.perform(get("/bachelor/image/getImage/?id=1", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))
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

	@Test
	@DisplayName("PUT /image/1 - Success")
	void testImagePutSuccess() throws Exception {
		// Setup mocked service
		Image puttImage = new Image(1, "mockPath", "Normal");
		Image mockgetImage = new Image(1, "mockPath", "Normal", 1);
		Image mockupdatedImage = new Image(1, "mockPath", "Normal", 2);

		doReturn(Optional.of(mockgetImage)).when(service).getImageById(1);
		doReturn(mockupdatedImage).when(service).update(any());
		
		mockMvc.perform(put("/bachelor/image/updateStatus").contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.IF_MATCH, 1).content(asJsonString(puttImage)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				.andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
				.andExpect(header().string(HttpHeaders.LOCATION, "/getImage/1"))

				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.physicalPath", is("mockPath")))
				.andExpect(jsonPath("$.status", is("Normal"))).andExpect(jsonPath("$.version", is(2)));

	}

	@Test
	@DisplayName("PUT /image/1 - Version Mismatch")
	void testImagePutVersionMismatch() throws Exception {
		Image puttImage = new Image(1, "mockPath", "Normal");
		Image mockgetImage = new Image(1, "mockPath", "Normal", 2);

		doReturn(Optional.of(mockgetImage)).when(service).getImageById(1);
		
		mockMvc.perform(put("/bachelor/image/updateStatus").contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.IF_MATCH, 1).content(asJsonString(puttImage)))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("PUT /product/1 - Not Found")
	void testImagePutNotFound() throws Exception {
		// Setup mocked service
		Image puttImage = new Image(1, "mockPath", "Normal");
		doReturn(Optional.empty()).when(service).getImageById(1);

		mockMvc.perform(put("/bachelor/image/updateStatus").contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.IF_MATCH, 1).content(asJsonString(puttImage)))

				// Validate the response code and content type
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("GET /getAllImages")
	void testGetAllImage() throws Exception {

		ArrayList<Image> mockImage = new ArrayList<Image>();
		mockImage.add(new Image(1, "mockPath", "Normal", 1));

		doReturn(mockImage).when(service).getAllImages();
		mockMvc.perform(get("/bachelor/image/getAllImages")).andExpect(status().isFound());

	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
