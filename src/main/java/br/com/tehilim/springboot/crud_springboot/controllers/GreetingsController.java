package br.com.tehilim.springboot.crud_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tehilim.springboot.crud_springboot.model.Usuario;
import br.com.tehilim.springboot.crud_springboot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injeção de Dependência */
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/a/b/c/d/e/f/g/h/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Olha o Spring Boot API! -> " + name + "!";
    }
    
    
    @RequestMapping(value = "/olamundo/{nomevar}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nomevar) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nomevar);
    	usuarioRepository.save(usuario);
    	
    	return "Olá, mundo! Assinado: " + nomevar;
    }
    
    
    @GetMapping(value = "listatodos") /* nosso primeiro médoto de API */
    @ResponseBody /* Retorna os dados para o corpo da resposta */
    public ResponseEntity<List<Usuario>> listaUsuario() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); /* Executa a consulta no BD */
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* Retorna a lista em JSON */
    }
    
    
    @PostMapping(value = "salvar") // mapeia a url
    @ResponseBody // descrição da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // recebe os dados para salvar
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    
    @PutMapping(value = "atualizar") // mapeia a url
    @ResponseBody // descrição da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // recebe os dados para salvar
    	
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado!", HttpStatus.OK);
    	}
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    
    @DeleteMapping(value = "delete") // mapeia a url
    @ResponseBody // descrição da resposta
    public ResponseEntity<String> delete(@RequestParam Long idUser) { // recebe o idUser para deletar
    	
    	usuarioRepository.deleteById(idUser);
    	
    	return new ResponseEntity<String>("Usuário <" + idUser + "> deletado com sucesso", HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscaruserid") // mapeia a url
    @ResponseBody // descrição da resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam (name = "idUser") Long idUser) { // recebe o idUser para deletar
    	
    	Usuario usuario = usuarioRepository.findById(idUser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarpornome") // mapeia a url
    @ResponseBody // descrição da resposta
    public ResponseEntity<List<Usuario>> buscarpornome(@RequestParam (name = "name") String name) { // recebe o idUser para deletar
    	
    	List<Usuario> listaUsuarios = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
    }
    
}
