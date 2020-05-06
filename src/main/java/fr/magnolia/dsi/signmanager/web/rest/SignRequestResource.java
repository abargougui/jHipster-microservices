package fr.magnolia.dsi.signmanager.web.rest;

import fr.magnolia.dsi.signmanager.domain.SignRequest;
import fr.magnolia.dsi.signmanager.repository.SignRequestRepository;
import fr.magnolia.dsi.signmanager.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.magnolia.dsi.signmanager.domain.SignRequest}.
 */
@RestController
@RequestMapping("/api")
public class SignRequestResource {

    private final Logger log = LoggerFactory.getLogger(SignRequestResource.class);

    private static final String ENTITY_NAME = "signmanagerSignRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignRequestRepository signRequestRepository;

    public SignRequestResource(SignRequestRepository signRequestRepository) {
        this.signRequestRepository = signRequestRepository;
    }

    /**
     * {@code POST  /sign-requests} : Create a new signRequest.
     *
     * @param signRequest the signRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signRequest, or with status {@code 400 (Bad Request)} if the signRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sign-requests")
    public ResponseEntity<SignRequest> createSignRequest(@Valid @RequestBody SignRequest signRequest) throws URISyntaxException {
        log.debug("REST request to save SignRequest : {}", signRequest);
        if (signRequest.getId() != null) {
            throw new BadRequestAlertException("A new signRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignRequest result = signRequestRepository.save(signRequest);
        return ResponseEntity.created(new URI("/api/sign-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sign-requests} : Updates an existing signRequest.
     *
     * @param signRequest the signRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signRequest,
     * or with status {@code 400 (Bad Request)} if the signRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sign-requests")
    public ResponseEntity<SignRequest> updateSignRequest(@Valid @RequestBody SignRequest signRequest) throws URISyntaxException {
        log.debug("REST request to update SignRequest : {}", signRequest);
        if (signRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignRequest result = signRequestRepository.save(signRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sign-requests} : get all the signRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signRequests in body.
     */
    @GetMapping("/sign-requests")
    public List<SignRequest> getAllSignRequests() {
        log.debug("REST request to get all SignRequests");
        return signRequestRepository.findAll();
    }

    /**
     * {@code GET  /sign-requests/:id} : get the "id" signRequest.
     *
     * @param id the id of the signRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sign-requests/{id}")
    public ResponseEntity<SignRequest> getSignRequest(@PathVariable String id) {
        log.debug("REST request to get SignRequest : {}", id);
        Optional<SignRequest> signRequest = signRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(signRequest);
    }

    /**
     * {@code DELETE  /sign-requests/:id} : delete the "id" signRequest.
     *
     * @param id the id of the signRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sign-requests/{id}")
    public ResponseEntity<Void> deleteSignRequest(@PathVariable String id) {
        log.debug("REST request to delete SignRequest : {}", id);
        signRequestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
