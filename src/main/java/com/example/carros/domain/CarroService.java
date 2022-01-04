package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros(){
        return rep.findAll();
    }

    public Optional<Carro> getCarrosById(Long id) {
        return rep.findById(id);
    }

    public List<Carro> getCarrosFake(){
        List<Carro> carros = new ArrayList<>();

        carros.add(new Carro(1L, "Fusca"));
        carros.add(new Carro(2L, "Brasilia"));
        carros.add(new Carro(3L, "Omega"));
        return carros;
    }

    public Iterable<Carro> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    public Carro insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possível inserir o registro");
        return rep.save(carro);
    }

    public Carro update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            // Atualiza o carro
            rep.save(db);

            return db;
        } else {
            //return null;
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }
}
