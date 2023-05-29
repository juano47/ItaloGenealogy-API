package com.delaiglesia.italogenealogy.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.delaiglesia.italogenealogy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HolderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HolderDTO.class);
        HolderDTO holderDTO1 = new HolderDTO();
        holderDTO1.setId(1L);
        HolderDTO holderDTO2 = new HolderDTO();
        assertThat(holderDTO1).isNotEqualTo(holderDTO2);
        holderDTO2.setId(holderDTO1.getId());
        assertThat(holderDTO1).isEqualTo(holderDTO2);
        holderDTO2.setId(2L);
        assertThat(holderDTO1).isNotEqualTo(holderDTO2);
        holderDTO1.setId(null);
        assertThat(holderDTO1).isNotEqualTo(holderDTO2);
    }
}
