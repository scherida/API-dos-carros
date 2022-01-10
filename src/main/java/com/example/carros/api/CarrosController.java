package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired //O Spring vai injetar essa dependência, ou seja, vai criar um objeto do tipo CarroService
    private CarroService service;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getCarros());
        //return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<CarroDTO> carro = service.getCarrosById(id);

//        new Carro(1L, "Nome", "esportivos");
        return carro.map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());

//        if(carro.isPresent()){
//            Carro c = carro.get();
//           return ResponseEntity.ok(c);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarrosByTipo(tipo);

        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro){ //@RequestBody - vai converter o json do carro para objeto
       try {
           CarroDTO c = service.insert(carro);
           URI location = getUri(c.getId());
           return ResponseEntity.created(location).build();
       }catch (Exception e){
           return ResponseEntity.badRequest().build();
       }
    }

    private URI getUri(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
        Carro c = service.update(carro, id);
        return "Carro atualizado com sucesso: " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        service.delete(id);
        return "Carro deletado com sucesso: ";
    }
}
