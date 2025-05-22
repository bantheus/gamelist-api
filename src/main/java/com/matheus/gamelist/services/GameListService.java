package com.matheus.gamelist.services;

import com.matheus.gamelist.dto.GameListDTO;
import com.matheus.gamelist.entities.GameList;
import com.matheus.gamelist.projections.GameMinProjection;
import com.matheus.gamelist.repositories.GameListRepository;
import com.matheus.gamelist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(GameListDTO::new).toList();
    }

    @Transactional
    public void move(Long listId, int sourceIndex, int destinationIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);

        GameMinProjection game = list.remove(sourceIndex);
        list.add(destinationIndex, game);

        int min = Math.min(sourceIndex, destinationIndex);
        int max = Math.max(sourceIndex, destinationIndex);

        for (int position = min; position <= max; position++) {
            gameListRepository.updateBelongingPosition(listId, list.get(position).getId(), null);
        }
    }
}