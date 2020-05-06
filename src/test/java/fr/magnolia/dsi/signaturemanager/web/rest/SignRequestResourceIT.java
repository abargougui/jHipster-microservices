package fr.magnolia.dsi.signaturemanager.web.rest;

import fr.magnolia.dsi.signaturemanager.SignaturemanagerApp;
import fr.magnolia.dsi.signaturemanager.domain.SignRequest;
import fr.magnolia.dsi.signaturemanager.repository.SignRequestRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SignRequestResource} REST controller.
 */
@SpringBootTest(classes = SignaturemanagerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SignRequestResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private SignRequestRepository signRequestRepository;

    @Autowired
    private MockMvc restSignRequestMockMvc;

    private SignRequest signRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignRequest createEntity() {
        SignRequest signRequest = new SignRequest()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
        return signRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignRequest createUpdatedEntity() {
        SignRequest signRequest = new SignRequest()
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE);
        return signRequest;
    }

    @BeforeEach
    public void initTest() {
        signRequestRepository.deleteAll();
        signRequest = createEntity();
    }

    @Test
    public void createSignRequest() throws Exception {
        int databaseSizeBeforeCreate = signRequestRepository.findAll().size();

        // Create the SignRequest
        restSignRequestMockMvc.perform(post("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signRequest)))
            .andExpect(status().isCreated());

        // Validate the SignRequest in the database
        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeCreate + 1);
        SignRequest testSignRequest = signRequestList.get(signRequestList.size() - 1);
        assertThat(testSignRequest.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSignRequest.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    public void createSignRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signRequestRepository.findAll().size();

        // Create the SignRequest with an existing ID
        signRequest.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignRequestMockMvc.perform(post("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signRequest)))
            .andExpect(status().isBadRequest());

        // Validate the SignRequest in the database
        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = signRequestRepository.findAll().size();
        // set the field null
        signRequest.setTitle(null);

        // Create the SignRequest, which fails.

        restSignRequestMockMvc.perform(post("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signRequest)))
            .andExpect(status().isBadRequest());

        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = signRequestRepository.findAll().size();
        // set the field null
        signRequest.setPrice(null);

        // Create the SignRequest, which fails.

        restSignRequestMockMvc.perform(post("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signRequest)))
            .andExpect(status().isBadRequest());

        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSignRequests() throws Exception {
        // Initialize the database
        signRequestRepository.save(signRequest);

        // Get all the signRequestList
        restSignRequestMockMvc.perform(get("/api/sign-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signRequest.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    public void getSignRequest() throws Exception {
        // Initialize the database
        signRequestRepository.save(signRequest);

        // Get the signRequest
        restSignRequestMockMvc.perform(get("/api/sign-requests/{id}", signRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signRequest.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    public void getNonExistingSignRequest() throws Exception {
        // Get the signRequest
        restSignRequestMockMvc.perform(get("/api/sign-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSignRequest() throws Exception {
        // Initialize the database
        signRequestRepository.save(signRequest);

        int databaseSizeBeforeUpdate = signRequestRepository.findAll().size();

        // Update the signRequest
        SignRequest updatedSignRequest = signRequestRepository.findById(signRequest.getId()).get();
        updatedSignRequest
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE);

        restSignRequestMockMvc.perform(put("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignRequest)))
            .andExpect(status().isOk());

        // Validate the SignRequest in the database
        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeUpdate);
        SignRequest testSignRequest = signRequestList.get(signRequestList.size() - 1);
        assertThat(testSignRequest.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSignRequest.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    public void updateNonExistingSignRequest() throws Exception {
        int databaseSizeBeforeUpdate = signRequestRepository.findAll().size();

        // Create the SignRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignRequestMockMvc.perform(put("/api/sign-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signRequest)))
            .andExpect(status().isBadRequest());

        // Validate the SignRequest in the database
        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSignRequest() throws Exception {
        // Initialize the database
        signRequestRepository.save(signRequest);

        int databaseSizeBeforeDelete = signRequestRepository.findAll().size();

        // Delete the signRequest
        restSignRequestMockMvc.perform(delete("/api/sign-requests/{id}", signRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignRequest> signRequestList = signRequestRepository.findAll();
        assertThat(signRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
