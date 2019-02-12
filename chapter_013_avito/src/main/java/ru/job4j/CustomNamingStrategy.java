package ru.job4j;

import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * Change naming strategy.
 * Added prefix to names tables in SQL.
 */
public class CustomNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    public static final String PREFIX = "av_";

    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier(PREFIX + stringForm, buildingContext);
    }

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        return super.transformEntityName(entityNaming);
    }

    @Override
    protected String transformAttributePath(AttributePath attributePath) {
        return super.transformAttributePath(attributePath);
    }
}