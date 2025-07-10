package br.ufra.edu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinas;

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinas.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
        return disciplinas.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Disciplina adicionar(@RequestBody Disciplina disciplina) {
        return disciplinas.save(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizar(@PathVariable Long id, @RequestBody Disciplina dadosAtualizados) {
        return disciplinas.findById(id)
                .map(disciplina -> {
                    disciplina.setNome(dadosAtualizados.getNome());
                    disciplina.setProfessor(dadosAtualizados.getProfessor());
                    disciplina.setIndice(dadosAtualizados.getIndice());
                    disciplina.setCodigo(dadosAtualizados.getCodigo());
                    Disciplina atualizado = disciplinas.save(disciplina);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        Disciplina disciplina = disciplinas.findById(id)
                .orElseThrow();
        disciplinas.delete(disciplina);
    }
}
