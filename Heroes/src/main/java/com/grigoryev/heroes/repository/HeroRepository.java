package com.grigoryev.heroes.repository;

import com.grigoryev.heroes.tables.pojos.HeroEntity;
import com.grigoryev.heroes.util.HikariConnectionManager;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

import static com.grigoryev.heroes.Tables.HERO_ENTITY;

public class HeroRepository {

    private final DSLContext dslContext;

    public HeroRepository() {
        dslContext = DSL.using(HikariConnectionManager.getConnection());
    }

    public Optional<HeroEntity> findById(Long id) {
        return dslContext.selectFrom(HERO_ENTITY)
                .where(HERO_ENTITY.ID.eq(id))
                .fetchOptional()
                .map(blogModelRecord -> blogModelRecord.into(HeroEntity.class));
    }

    public List<HeroEntity> findAll() {
        return dslContext.selectFrom(HERO_ENTITY)
                .fetchInto(HeroEntity.class);
    }

    public Optional<HeroEntity> save(HeroEntity heroEntity) {
        return dslContext.insertInto(HERO_ENTITY)
                .set(dslContext.newRecord(HERO_ENTITY, heroEntity))
                .returning()
                .fetchOptional()
                .map(r -> r.into(HeroEntity.class));
    }

    public Optional<HeroEntity> updateById(HeroEntity heroEntity) {
        return dslContext.update(HERO_ENTITY)
                .set(dslContext.newRecord(HERO_ENTITY, heroEntity))
                .where(HERO_ENTITY.ID.eq(heroEntity.getId()))
                .returning()
                .fetchOptional()
                .map(r -> r.into(HeroEntity.class));
    }

    public Optional<HeroEntity> deleteById(Long id) {
        return dslContext.deleteFrom(HERO_ENTITY)
                .where(HERO_ENTITY.ID.eq(id))
                .returning()
                .fetchOptional()
                .map(r -> r.into(HeroEntity.class));
    }

}
