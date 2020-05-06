package fr.magnolia.dsi.signaturemanager.web.rest;

import fr.magnolia.dsi.signaturemanager.domain.SignatureRequest;
import fr.magnolia.dsi.signaturemanager.repository.SignatureRequestRepository;
import fr.magnolia.dsi.signaturemanager.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link fr.magnolia.dsi.signaturemanager.domain.SignatureRequest}.
 */
@RestController
@RequestMapping("/api")
public class SignatureRequestResource {

    private final Logger log = LoggerFactory.getLogger(SignatureRequestResource.class);

    private static final String ENTITY_NAME = "signmanagerSignatureRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignatureRequestRepository signatureRequestRepository;

    public SignatureRequestResource(SignatureRequestRepository signatureRequestRepository) {
        this.signatureRequestRepository = signatureRequestRepository;
    }

    /**
     * {@code POST  /signature-requests} : Create a new signatureRequest.
     *
     * @param signatureRequest the signatureRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signatureRequest, or with status {@code 400 (Bad Request)} if the signatureRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signature-requests")
    public ResponseEntity<SignatureRequest> createSignatureRequest(@Valid @RequestBody SignatureRequest signatureRequest) throws URISyntaxException {
        log.debug("REST request to save SignatureRequest : {}", signatureRequest);
        if (signatureRequest.getId() != null) {
            throw new BadRequestAlertException("A new signatureRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignatureRequest result = signatureRequestRepository.save(signatureRequest);
        return ResponseEntity.created(new URI("/api/signature-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signature-requests} : Updates an existing signatureRequest.
     *
     * @param signatureRequest the signatureRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signatureRequest,
     * or with status {@code 400 (Bad Request)} if the signatureRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signatureRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signature-requests")
    public ResponseEntity<SignatureRequest> updateSignatureRequest(@Valid @RequestBody SignatureRequest signatureRequest) throws URISyntaxException {
        log.debug("REST request to update SignatureRequest : {}", signatureRequest);
        if (signatureRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignatureRequest result = signatureRequestRepository.save(signatureRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signatureRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /signature-requests} : get all the signatureRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signatureRequests in body.
     */
    @GetMapping("/signature-requests")
    public List<SignatureRequest> getAllSignatureRequests() {
        log.debug("REST request to get all SignatureRequests");
        return signatureRequestRepository.findAll();
    }

    /**
     * {@code GET  /signature-requests/:id} : get the "id" signatureRequest.
     *
     * @param id the id of the signatureRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signatureRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signature-requests/{id}")
    public ResponseEntity<SignatureRequest> getSignatureRequest(@PathVariable String id) {
        log.debug("REST request to get SignatureRequest : {}", id);
        Optional<SignatureRequest> signatureRequest = signatureRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(signatureRequest);
    }

    /**
     * {@code DELETE  /signature-requests/:id} : delete the "id" signatureRequest.
     *
     * @param id the id of the signatureRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signature-requests/{id}")
    public ResponseEntity<Void> deleteSignatureRequest(@PathVariable String id) {
        log.debug("REST request to delete SignatureRequest : {}", id);
        signatureRequestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
