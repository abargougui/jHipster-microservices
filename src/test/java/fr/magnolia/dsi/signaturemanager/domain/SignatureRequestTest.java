package fr.magnolia.dsi.signaturemanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.magnolia.dsi.signaturemanager.web.rest.TestUtil;

public class SignatureRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignatureRequest.class);
        SignatureRequest signatureRequest1 = new SignatureRequest();
        signatureRequest1.setId("id1");
        SignatureRequest signatureRequest2 = new SignatureRequest();
        signatureRequest2.setId(signatureRequest1.getId());
        assertThat(signatureRequest1).isEqualTo(signatureRequest2);
        signatureRequest2.setId("id2");
        assertThat(signatureRequest1).isNotEqualTo(signatureRequest2);
        signatureRequest1.setId(null);
        assertThat(signatureRequest1).isNotEqualTo(signatureRequest2);
    }
}
