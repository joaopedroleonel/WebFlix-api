package com.webflix.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:schema;MODE=MySQL;DATABASE_TO_LOWER=false;DATABASE_TO_UPPER=false;CASE_INSENSITIVE_IDENTIFIERS=TRUE;DB_CLOSE_DELAY=-1",
		"spring.jpa.hibernate.ddl-auto=validate",
		"spring.sql.init.mode=always",
		"spring.sql.init.schema-locations=file:db/init.sql" })
class SchemaValidationTest {

	@Test
	void initSqlMatchesEntityMapping() {
	}

}
