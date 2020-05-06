package fr.magnolia.dsi.signaturemanager.repository;

import fr.magnolia.dsi.signaturemanager.domain.SignRequest;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SignRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignRequestRepository extends MongoRepository<SignRequest, String> {
}
