/*
 * The MIT License
 *
 * Copyright 2016 Universidad de los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.zk.competition.models;

import co.edu.uniandes.isis2503.zk.competition.models.converter.CompetitionConverter;
import co.edu.uniandes.isis2503.zk.competition.models.dtos.CompetitionDTO;
import co.edu.uniandes.isis2503.zk.competition.models.dtos.CompetitorDTO;
import co.edu.uniandes.isis2503.zk.competition.models.entities.Competition;
import co.edu.uniandes.isis2503.zk.competition.persistences.CompetitionPersistence;
import java.util.List;

/**
 *
 * @author Luis Felipe Mendivelso Osorio <lf.mendivelso10@uniandes.edu.co>
 */
public class CompetitionLogic {
    
    private final CompetitionPersistence persistencer;
    
    public CompetitionLogic(){
        this.persistencer = CompetitionPersistence.getPesistencer();
    }
    
    public CompetitionDTO createCompetition(CompetitionDTO competition) {
        Competition entity = CompetitionConverter.DTOToEntity(competition);
        entity = persistencer.createCompetition(entity);
        competition = CompetitionConverter.entityToDTO(entity);
        return competition;
    }

    public CompetitionDTO updateCompetition(CompetitionDTO competition) {
        Competition entity = CompetitionConverter.DTOToEntity(competition);
        entity = persistencer.updateCompetition(entity);
        competition = CompetitionConverter.entityToDTO(entity);
        return competition;
    }

    public void deleteCompetition(CompetitionDTO competition) {
        persistencer.deleteCompetition(CompetitionConverter.DTOToEntity(competition));
    }

    public CompetitionDTO getCompetitionById(String id) {
        return CompetitionConverter.entityToDTO(persistencer.getCompetitionById(id));
    }
    
    public CompetitionDTO getCompetitionByName(String name) {
        return CompetitionConverter.entityToDTO(persistencer.getCompetitionByName(name));
    }
    
    public List<CompetitionDTO> getCompetitions() {
        return CompetitionConverter.listEntitiestoListDTO(persistencer.getCompetitions());
    }
    
    public boolean addCompetitorToCompetition(CompetitionDTO competition, CompetitorDTO competitor) {
        try {
            Competition entity = CompetitionConverter.DTOToEntity(competition);
            entity.getCompetitors().add(competitor.getId());
            entity = persistencer.updateCompetition(entity);
            competition = CompetitionConverter.entityToDTO(entity);
            return true;
        }
        catch(Exception e) {
            return false;
        }        
    }
}