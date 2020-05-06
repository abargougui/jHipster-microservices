package fr.magnolia.dsi.signaturemanager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A SignatureRequest.
 */
@Document(collection = "signature_request")
public class SignatureRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("transaction_id")
    private String transactionId;

    @NotNull
    @Field("request_date")
    private LocalDate requestDate;

    @NotNull
    @Field("status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public SignatureRequest transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public SignatureRequest requestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public SignatureRequest status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureRequest)) {
            return false;
        }
        return id != null && id.equals(((SignatureRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SignatureRequest{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
