package com.delaiglesia.italogenealogy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.delaiglesia.italogenealogy.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HolderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Holder.class);
        Holder holder1 = new Holder();
        holder1.setId(1L);
        Holder holder2 = new Holder();
        holder2.setId(holder1.getId());
        assertThat(holder1).isEqualTo(holder2);
        holder2.setId(2L);
        assertThat(holder1).isNotEqualTo(holder2);
        holder1.setId(null);
        assertThat(holder1).isNotEqualTo(holder2);
    }
}
