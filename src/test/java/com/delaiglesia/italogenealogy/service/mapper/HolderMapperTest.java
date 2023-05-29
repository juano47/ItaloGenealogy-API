package com.delaiglesia.italogenealogy.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HolderMapperTest {

    private HolderMapper holderMapper;

    @BeforeEach
    public void setUp() {
        holderMapper = new HolderMapperImpl();
    }
}
