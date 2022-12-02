package br.com.gregoriohd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoriohd.service.CandidatoService;
import br.com.gregoriohd.shared.CandidatoDTO;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

	@Autowired
	private CandidatoService candidatoService;

	@GetMapping
	public List<CandidatoDTO> listaTotalPorEstado() {
		System.out.println(candidatoService.listaPercentual());
		return candidatoService.listaTodos();
	}
	
	@GetMapping("/{estado}")
	public List<CandidatoDTO> listaTotalPorEstado(@PathVariable String estado) {
		return candidatoService.listarPorEstado(estado);
	}
	
	@GetMapping("/tipo/{sangue}")
	public List<CandidatoDTO> listarPorTipoSanguineo(@PathVariable String sangue) {
		return candidatoService.listarPorTipoSanguineo(sangue);
	}
	
	@GetMapping("/porcentagem")
	public String percentualObesosPorSexo() {
		return candidatoService.listaPercentual();
	}
	
	@GetMapping("/media/{sangue}")
	public String mediaDeIdadePorTipoSangue(@PathVariable String sangue) {
		return candidatoService.mediaDeIdadePorTipoSangue(sangue);
	}

	@GetMapping("/importar")
	public ResponseEntity<?> importar() {
		candidatoService.importar();
		return new ResponseEntity<>("Import realizado com sucesso!", HttpStatus.CREATED);
	}
}
