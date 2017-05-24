package py.pol.una.ii.pw.rest;

import javax.inject.Inject;
import javax.ws.rs.Path;

import py.pol.una.ii.pw.data.Repositorio;

@Path("/compra")
public class Compra {
	@Inject
	Repositorio repositorio;
	
}
