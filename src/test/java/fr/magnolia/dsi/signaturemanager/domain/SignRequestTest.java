package fr.magnolia.dsi.signaturemanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.magnolia.dsi.signaturemanager.web.rest.TestUtil;

public class SignRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignRequest.class);
        SignRequest signRequest1 = new SignRequest();
        signRequest1.setId("id1");
        SignRequest signRequest2 = new SignRequest();
        signRequest2.setId(signRequest1.getId());
        assertThat(signRequest1).isEqualTo(signRequest2);
        signRequest2.setId("id2");
        assertThat(signRequest1).isNotEqualTo(signRequest2);
        signRequest1.setId(null);
        assertThat(signRequest1).isNotEqualTo(signRequest2);
    }
}
