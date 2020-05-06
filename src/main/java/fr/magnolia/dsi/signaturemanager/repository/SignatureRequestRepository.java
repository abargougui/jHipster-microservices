package fr.magnolia.dsi.signaturemanager.repository;

import fr.magnolia.dsi.signaturemanager.domain.SignatureRequest;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SignatureRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignatureRequestRepository extends MongoRepository<SignatureRequest, String> {
}
