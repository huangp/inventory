package com.github.huangp.inventory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.huangp.inventory.dto.InventoryItemDto;
import com.github.huangp.inventory.dto.ManufacturerDto;
import com.github.huangp.inventory.model.Manufacturer;
import com.github.huangp.inventory.repository.InventoryRepository;
import com.github.huangp.inventory.repository.ManufacturerRepository;
import com.github.huangp.inventory.web.InventoryController;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QutInventoryApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPost() throws Exception {
        HttpEntity<InventoryItemDto> entity = new HttpEntity<>(inventory("stuff"), headers);

        String url = "http://localhost:" + port + "/inventory";
        ResponseEntity<InventoryItemDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

	@Test
	public void testPostInvalidData() throws Exception {
    	// empty name is invalid
		HttpEntity<InventoryItemDto> entity = new HttpEntity<>(inventory(""), headers);

		String url = "http://localhost:" + port + "/inventory";
		ResponseEntity<InventoryItemDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testPostSameItemAgain() throws Exception {
		HttpEntity<InventoryItemDto> entity = new HttpEntity<>(inventory("conflict item"), headers);

		String url = "http://localhost:" + port + "/inventory";
		ResponseEntity<InventoryItemDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		response = restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

	}

	@Test
	public void testGetSingleItem() throws Exception {
		HttpEntity<InventoryItemDto> entity = new HttpEntity<>(inventory("get item"), headers);

		String url = "http://localhost:" + port + "/inventory";
		ResponseEntity<InventoryItemDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		URI location = response.getHeaders().getLocation();
		Assertions.assertThat(location).isNotNull();

		ResponseEntity<InventoryItemDto> getResponse = restTemplate.exchange(location.toString(), HttpMethod.GET, null, ParameterizedTypeReference.forType(InventoryItemDto.class));

		Assertions.assertThat(getResponse.getBody().getId()).isNotBlank();
		Assertions.assertThat(getResponse.getBody().getName()).isEqualTo("get item");
	}

	@Test
	public void testGetItemNotFound() throws Exception {
		String url = "http://localhost:" + port + "/inventory/not-exist-id";
		ResponseEntity<InventoryItemDto> response = restTemplate.exchange(url, HttpMethod.GET, null, InventoryItemDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetAll() throws Exception {
		HttpEntity<InventoryItemDto> entity = new HttpEntity<>(inventory("at least one"), headers);

		String url = "http://localhost:" + port + "/inventory";
		restTemplate.exchange(url, HttpMethod.POST, entity, InventoryItemDto.class);

		ResponseEntity<List<InventoryItemDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InventoryItemDto>>() {});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).hasSizeGreaterThanOrEqualTo(1);

	}

    private InventoryItemDto inventory(String name) throws JsonProcessingException {
        ManufacturerDto manufacturerDto = new ManufacturerDto("acme corp", "https://example.com", "+61 44444444");
        return new InventoryItemDto(null, name, null, manufacturerDto);
    }


}
