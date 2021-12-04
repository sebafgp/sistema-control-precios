
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Canje;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Recompensa;
import com.backendigans.Sistema_Control_De_Precios.model.Vista;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioCanje;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioRecompensa;
import com.backendigans.Sistema_Control_De_Precios.exceptions.*;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/colaborador")
public class ControladorColaborador {
    @Autowired
    ServicioColaborador colaboradorService;

    @Autowired
    ServicioActualizacion actualizacionService;

    @Autowired
    ServicioRecompensa recompensaService;

    @Autowired
    ServicioCanje canjeService;

    @JsonView(Vista.Colaborador.class)
    @GetMapping("")
    public List<Colaborador> list() {
        return colaboradorService.listAllColaboradores();
    }

    @JsonView(Vista.Colaborador.class)
    @GetMapping("/{colaboradorID}")
    public ResponseEntity<Colaborador> get(@PathVariable Integer colaboradorID) {
        try {
            Colaborador colaborador = colaboradorService.getColaborador(colaboradorID);
            return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Colaborador>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Colaborador> add(@RequestBody Colaborador colaborador) {
        try {
            colaboradorService.saveColaborador(colaborador);
            return new ResponseEntity<>(colaborador, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(colaborador, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{colaboradorID}")
    public ResponseEntity<?> update(@RequestBody Colaborador colaborador, @PathVariable Integer colaboradorID) {
        try {
            Colaborador existColaborador = colaboradorService.getColaborador(colaboradorID);
            colaborador.setColaboradorID(colaboradorID);
            colaboradorService.saveColaborador(colaborador);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer colaboradorID) {

        colaboradorService.deleteColaborador(colaboradorID);
    }

    @JsonView(Vista.Actualizacion.class)
    @GetMapping("/valoraciones/{colaboradorID}")
    public ResponseEntity<List<Actualizacion>> getValoraciones(@PathVariable Integer colaboradorID) {
        try {
            Colaborador colaborador = colaboradorService.getColaborador(colaboradorID);
            List<Actualizacion> list = actualizacionService.encontrarPorColaborador(colaborador);
            return new ResponseEntity<List<Actualizacion>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Actualizacion>>(HttpStatus.NOT_FOUND);
        }
    }

    /*Retornar lista de LinkedHashMap<String, Object>
    {
        {   <"nickname1", nickname1>
            <"reputacion1", reputacion1>}
        {   <"nickname2", nickname2>
            <"reputacion2", reputacion2>}
        {   <"nickname3", nickname3>
            <"reputacion3", reputacion3>}
    }
*/
    @GetMapping("/topColaboradores")
    public ResponseEntity<List<LinkedHashMap<String, Object>>> getTopColaboradores() {
        try {
            List<Colaborador> colaboradores = colaboradorService.getTopColaboradores();
            if (!colaboradores.isEmpty()) {
                List<LinkedHashMap<String, Object>> topTres = new ArrayList<>();
                for (int i = 0; i < colaboradores.size(); i++) {
                    LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                    map.put("nickname", colaboradores.get(i).getNickname());
                    map.put("reputacion", colaboradores.get(i).getReputacion());
                    topTres.add(map);
                }
                return new ResponseEntity<List<LinkedHashMap<String, Object>>>(topTres, HttpStatus.OK);
            } else {
                throw new NoSuchElementException();
            }

        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<LinkedHashMap<String, Object>>>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(Vista.Colaborador.class)
    @GetMapping("/reputacionYActualizacionesDeColaboradorPorNickname/{nickname}")
    public ResponseEntity<LinkedHashMap<String, Object>> getReputacionYActualizacionesDeColaboradorPorNickname(@PathVariable String nickname) {
        try {
            Colaborador colaborador = colaboradorService.getColaboradorByNickname(nickname);

            if(colaborador!=null){
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("nickname", colaborador.getNickname());
                map.put("reputacion", colaborador.getReputacion());
                map.put("actualizaciones", colaborador.getActualizaciones());
                return new ResponseEntity<LinkedHashMap<String, Object>>(map, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<LinkedHashMap<String, Object>>(HttpStatus.NOT_FOUND);
        }
    }

    static public class updateContrasenaWrapper {
        String email;
        String contrasena;
        String nuevaContrasena;

		public updateContrasenaWrapper() {
		}

		public updateContrasenaWrapper(String email, String contrasena, String nuevaContrasena) {
            this.email = email;
            this.contrasena = contrasena;
            this.nuevaContrasena = nuevaContrasena;
		}
		
		public String getEmail() {
			return this.email;
		}

		public String getContrasena() {
			return this.contrasena;
		}

		public String getNuevaContrasena() {
			return this.nuevaContrasena;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}

		public void setNuevaContrasena(String nuevaContrasena) {
			this.nuevaContrasena = nuevaContrasena;
		}
    }
    @PutMapping("/updateContrasena")
    public ResponseEntity<?> updateContrasena(@RequestBody updateContrasenaWrapper datos ) {
    	String email = datos.email;
    	String contrasena = datos.contrasena;
    	String nuevaContrasena = datos.nuevaContrasena;
        if (nuevaContrasena.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Colaborador colaborador = colaboradorService.buscarColaboradorPorEmail(email, contrasena);
            colaborador.setContrasena(nuevaContrasena);
            colaboradorService.saveColaborador(colaborador);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    static public class updateNicknameWrapper {
        String email;
        String contrasena;
        String nuevoNickname;

		public updateNicknameWrapper(String email, String contrasena, String nuevoNickname) {
            this.email = email;
            this.contrasena = contrasena;
            this.nuevoNickname = nuevoNickname;
		}
    }
    @PutMapping("/updateNickname")
    public ResponseEntity<?> updateNickname(@RequestBody updateNicknameWrapper datos ) {
    	String email = datos.email;
    	String contrasena = datos.contrasena;
    	String nuevoNickname = datos.nuevoNickname;
        if (nuevoNickname.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Colaborador colaborador = colaboradorService.buscarColaboradorPorEmail(email, contrasena);
            colaborador.setNickname(nuevoNickname);
            colaboradorService.saveColaborador(colaborador);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    static public class CanjearRecompensaWrapper {
        String email;
        String contrasena;
        int recompensaID;

		public CanjearRecompensaWrapper(String email, String contrasena, int recompensaID) {
            this.email = email;
            this.contrasena = contrasena;
            this.recompensaID = recompensaID;
		}

        public CanjearRecompensaWrapper() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        public int getRecompensaID() {
            return recompensaID;
        }

        public void setRecompensaID(int recompensaID) {
            this.recompensaID = recompensaID;
        }
    }

    @PostMapping("/canjearRecompensa")
    public ResponseEntity<Object> canjearRecompensa(@RequestBody CanjearRecompensaWrapper datos) {
        String email = datos.email;
        String contrasena = datos.contrasena;
        int recompensaID = datos.recompensaID;

        try {
            Colaborador colaborador = colaboradorService.buscarColaboradorPorEmail(email, contrasena);
            Recompensa recompensa = recompensaService.getRecompensa(recompensaID);

            if (colaborador.getPuntos()>= recompensa.getCosto()){
                if(!(recompensa.getStock() > 0)) throw new RecompensaSinStockException();
                Canje canje = new Canje(colaborador, recompensa);
                colaborador.addCanje(canje);
                recompensa.addCanje(canje);
                recompensa.disminuirStock(1);
                colaborador.disminuirPuntos(recompensa.getCosto());

                colaboradorService.saveColaborador(colaborador);
                recompensaService.saveRecompensa(recompensa);
                canjeService.saveCanje(canje);

                return new ResponseEntity<>(HttpStatus.OK);

            }
            else{
                throw new InsuficientesPuntosException();
            }
           
        } catch (NoSuchElementException | InsuficientesPuntosException | RecompensaSinStockException e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


}
