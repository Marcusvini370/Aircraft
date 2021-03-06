package com.br.aircraft.api.controller;

import static com.br.aircraft.api.domain.enums.EnumRelatorio.DISPONIVEIS;
import static com.br.aircraft.api.domain.enums.EnumRelatorio.SEMANAL;
import static com.br.aircraft.api.domain.enums.EnumRelatorio.VENDIDAS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.br.aircraft.api.domain.dto.AeronaveDTO;
import com.br.aircraft.api.domain.dto.input.AeronaveInput;
import com.br.aircraft.api.domain.dto.search.GrupoDTO;
import com.br.aircraft.api.domain.dto.search.GrupoNaoVendidasDTO;
import com.br.aircraft.api.domain.dto.search.GrupoSemanaDTO;
import com.br.aircraft.api.domain.service.AeronaveService;
import com.br.aircraft.api.domain.service.relatorioService;
import com.br.aircraft.api.swagger.controller.AeronaveControllerSwagger;

@RestController
@RequestMapping(value = "/aeronaves", produces = MediaType.APPLICATION_JSON_VALUE)
public class AeronaveController implements AeronaveControllerSwagger {

	private final AeronaveService aeronaveService;

	public AeronaveController(AeronaveService aeronaveService) {
		this.aeronaveService = aeronaveService;
	}

	@Autowired
	private relatorioService relatorioService;

	@GetMapping
	public ResponseEntity<List<AeronaveDTO>> findAll() {
		return ResponseEntity.ok(aeronaveService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AeronaveDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(aeronaveService.findById(id));
	}

	@PostMapping
	public ResponseEntity<AeronaveDTO> saveAeronave(@RequestBody @Valid AeronaveInput aeronaveInput) {
		return ResponseEntity.status(HttpStatus.CREATED).body(aeronaveService.save(aeronaveInput));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AeronaveDTO> updateAeronave(@PathVariable Long id,
			@RequestBody @Valid AeronaveInput aeronaveInput) {
		return ResponseEntity.ok(aeronaveService.update(id, aeronaveInput));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		aeronaveService.delete(id);
	}

	@GetMapping("/registro-semanal")
	public ResponseEntity<GrupoSemanaDTO> findRegistroSemanal() {
		return ResponseEntity.ok(aeronaveService.findRegistroSemanal());
	}

	@GetMapping("/marca-quantidade")
	public ResponseEntity<List<GrupoDTO>> findMarcaQuantidade() {
		return ResponseEntity.ok(aeronaveService.findMarcaQuantidade());
	}

	@GetMapping("/decada")
	public ResponseEntity<List<GrupoDTO>> findDecada() {
		return ResponseEntity.ok(aeronaveService.findDecada());
	}

	@GetMapping("/nao-vendidas")
	public ResponseEntity<GrupoNaoVendidasDTO> findNoSellers() {
		return ResponseEntity.ok(aeronaveService.findNoSellers());
	}

	@GetMapping("/find/{nome}")
	public ResponseEntity<List<AeronaveDTO>> findModel(@PathVariable String nome) {
		return ResponseEntity.ok(aeronaveService.findByModel(nome));
	}

	@GetMapping("/page")
	public ResponseEntity<Page<AeronaveDTO>> listarAeronavesPage(
			@PageableDefault(size = 5, sort = "nome") Pageable pageable) {
		return ResponseEntity.ok(aeronaveService.findAllPage(pageable));
	}

	@GetMapping("/find/{nome}/page")
	public ResponseEntity<Page<AeronaveDTO>> findModeloPage(@PathVariable String nome,
			@PageableDefault(size = 5, sort = "nome") Pageable pageable) {
		return ResponseEntity.ok(aeronaveService.findByNomeContaining(nome, pageable));
	}

	@GetMapping("/relatorio/vendidas")
	public ResponseEntity<String> pdfAeronavesVendidas(HttpServletRequest request) throws Exception {
		return new ResponseEntity<String>(relatorioService.pdfAeronaves(request, VENDIDAS), HttpStatus.OK);
	}

	@GetMapping("/relatorio/nao-vendidas")
	public ResponseEntity<String> pdfAeronavesNaoVendias(HttpServletRequest request) throws Exception {
		return new ResponseEntity<String>(relatorioService.pdfAeronaves(request, DISPONIVEIS), HttpStatus.OK);
	}

	@GetMapping("/relatorio/semanal")
	public ResponseEntity<String> pdfAeronavesSemanal(HttpServletRequest request) throws Exception {
		return new ResponseEntity<String>(relatorioService.pdfAeronaves(request, SEMANAL), HttpStatus.OK);
	}

}
