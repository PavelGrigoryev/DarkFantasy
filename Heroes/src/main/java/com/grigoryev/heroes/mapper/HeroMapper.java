package com.grigoryev.heroes.mapper;

import com.grigoryev.heroes.Hero;
import com.grigoryev.heroes.SaveHeroRequest;
import com.grigoryev.heroes.tables.pojos.HeroEntity;
import org.mapstruct.Mapper;

@Mapper
public interface HeroMapper {

    Hero toHero(HeroEntity entity);

    HeroEntity toHeroEntity(SaveHeroRequest request);

    HeroEntity toHeroEntity(Hero hero);

}
