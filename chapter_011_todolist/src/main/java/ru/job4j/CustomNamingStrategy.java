package ru.job4j;
import org.hibernate.boot.model.naming.*;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * Change naming strategy.
 * Added prefix to names tables in SQL.
 */
public class CustomNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private static final String PREFIX = "dbo_";

    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier(PREFIX + stringForm, buildingContext);
    }
}