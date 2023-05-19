package com.delaiglesia.italogenealogy;

import com.delaiglesia.italogenealogy.ItaloGenealogyApp;
import com.delaiglesia.italogenealogy.config.AsyncSyncConfiguration;
import com.delaiglesia.italogenealogy.config.EmbeddedSQL;
import com.delaiglesia.italogenealogy.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ItaloGenealogyApp.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
