package com.bachelor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.service.ImgService;

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
		// Setup our mocked service
		Image mockProduct = new Image(1, "Path", "Normal");
        doReturn(Optional.of(mockProduct)).when(service).getImageById(1);

		// Execute the GET request
        mockMvc.perform(get("/getImage/{id}", 1).accept(MediaType.APPLICATION_JSON_UTF8))

				// Validate the response code and content type
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

				// Validate the returned fields
//				.andExpect(jsonPath("$.physicalPath", is("Path")));
//				.andExpect(jsonPath("$.status", is("Normal")));
	}
	
//	
//	@Test
//    public void addApplication() throws Exception {
//        String mockImage = "{\"physicalPath\":\"path\",\"status\":\"Normal\"}";
//
//        //Create a post request with an accept header for application\json
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/bachelor/folderPath/saubmitImage")
//                .accept(MediaType.APPLICATION_JSON).content(mockImage)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        //Assert that the return status is CREATED
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//        //Get the location from response header and assert that it contains the URI of the created resource
////        assertEquals("http://localhost/application/1",
////                response.getHeader(HttpHeaders.LOCATION));
//    }

}
