package py.pol.una.ii.pw.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import py.pol.una.ii.pw.controller.IdentificadorCompras;
import py.pol.una.ii.pw.service.ControladorCompraMass;
import py.pol.una.ii.pw.service.MyControladorCompraMass;

@Path("/compraMass")
public class ComprasMasivasREST {
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/home/fernando/Documentos/";
	
	@Inject
	IdentificadorCompras identificador;
	@Inject
	MyControladorCompraMass controladorCompras;
	@Inject
    Logger log;

	@POST
	@Path("/cargar")
	@Consumes("multipart/form-data")
	public Response cargarCompras(MultipartFormDataInput input) {

		String nombre = "";

		Map<String, List<InputPart>> parts = input.getFormDataMap();

		List<InputPart> formPart = parts.get("file");
		String output="";

		for (InputPart inputPart : formPart) {

			 try {

				// Retrieve headers, read the Content-Disposition header to obtain the original name of the file
				 MultivaluedMap<String, String> cabeceras = inputPart.getHeaders();
				nombre = parseFileName(cabeceras);
				 
				// Handle the body of that part with an InputStream
				InputStream archivo = inputPart.getBody(InputStream.class,null);

				nombre = SERVER_UPLOAD_LOCATION_FOLDER + nombre + identificador.getIndentificadorNuevo();
				
				saveFile(archivo,nombre);
				controladorCompras.guardarCompras(nombre);
				output = "File saved to server location : " + nombre;
			  }catch (Exception e){
				 log.info("Hubo un error al guardar las compras: "+e.getMessage());
				 output ="Hubo un error al guardar las compras: "+e.getMessage();
				 e.printStackTrace();
			  }

			}

                

		return Response.status(200).entity(output).build();
	}
	// Parse Content-Disposition header to get the original file name
	private String parseFileName(MultivaluedMap<String, String> headers) {

		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {

				String[] tmp = name.split("=");

				String fileName = tmp[1].trim().replaceAll("\"","");

				return fileName;
			}
		}
		return "randomName";
	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
		String serverLocation) throws Exception{
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
				System.out.println("datos escritos: "+bytes.toString()+ " tamaño: "+bytes.length);
				
			}
			outpuStream.flush();
			outpuStream.close();
		
	}

}
