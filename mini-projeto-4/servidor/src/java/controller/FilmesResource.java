/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Filme;
import org.hibernate.Query;

/**
 * REST Web Service
 *
 * @author Felipe
 */
@Path("/filmes")
public class FilmesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FilmesResource
     */
    public FilmesResource() {
    }

    /**
     * Retrieves representation of an instance of controller.FilmesResource
     * @return an instance of java.lang.String
     */
    @Path("consulta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@QueryParam("texto")String texto,@QueryParam("criterio")String criterio) {
        
        Query q;
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        session.getTransaction().begin();
        switch (criterio){
            case "1":
                q = session.createSQLQuery("select * from filme where filme.titulo = :titulo")
                        .setParameter("titulo", texto);
                break;
            case "2":
                q = session.createSQLQuery("select * from filme where filme.genero = :genero")
                        .setParameter("genero", texto);
                break;
            case "3":
                q = session.createSQLQuery("select * from filme where filme.diretor = :diretor")
                        .setParameter("diretor", texto);
                break;
            case "4":
                q = session.createSQLQuery("select * from filme where filme.ano = :ano")
                        .setParameter("ano", texto);
                break;
            default:
                q = session.createSQLQuery("select * from filme");
        }
        List<Object[]> rows = q.list();
        
        return Response.ok(rows, MediaType.APPLICATION_JSON).build();
    }

    /**
     * POST method for creating an instance of FilmeResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(@FormParam("titulo")String titulo, @FormParam("diretor")String diretor,
            @FormParam("genero")String genero, @FormParam("estudio")String estudio, @FormParam("ano")String ano) {
        
        Filme filme = new Filme();
        filme.setAno(ano);
        filme.setDiretor(diretor);
        filme.setEstudio(estudio);
        filme.setGenero(genero);
        filme.setTitulo(titulo);
        
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session ses = sessionF.openSession();
        
        ses.getTransaction().begin();
        ses.save(filme);
        ses.getTransaction().commit();
        ses.close();
        
        return Response.ok(filme, MediaType.APPLICATION_JSON).build();
        
    }

    
    /**
     * Sub-resource locator method for {id}
     */
    @Path("id")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Response getFilmeResource(@QueryParam("id")String id, @QueryParam("formato")String formato) {
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        session.getTransaction().begin();
        Long idL = Long.parseLong(id);
        Query q = session.createSQLQuery("select * from filme where filme.id = :id").setParameter("id",idL);
        List<Object> filmes = q.list();
        
        Object filme = filmes.get(0);
        if (filme != null) {
            if (formato.equals("json"))
                return Response.ok(filme, MediaType.APPLICATION_JSON).build();
            else if(formato.equals("xml"))
                return Response.ok(filme, MediaType.TEXT_XML).build();
        } else {
            return Response.status(400, "Filme não encontrado.").build();
        }
        
        return null;
    }
    
    @POST
    @Path("atualiza")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFilme(@FormParam("id")String ids,@FormParam("titulo")String titulo){
         SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        Long id = Long.parseLong(ids);
        
        session.getTransaction().begin();
        Query q = session.createSQLQuery("update filme set titulo = :titulo where filme.id = :id")
                        .setParameter("id", id)
                        .setParameter("titulo", titulo);
        
        q.executeUpdate();
        session.getTransaction().commit();
        q = session.createSQLQuery("select * from filme where filme.id = :id").setParameter("id",id);
        List<Object> filmes = q.list();
        Object filme = filmes.get(0);
        session.close();
        
        return Response.ok(filme, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFilme(@FormParam("idr")String ids){
         SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        System.out.println(ids);
        int id = Integer.parseInt(ids);
        
        session.getTransaction().begin();
        Query q = session.createSQLQuery("select * from filme where filme.id = :id").setParameter("id",id);
        List<Object> filmes = q.list();
        Object filme = filmes.get(0);
        q = session.createSQLQuery("delete from filme where filme.id = :id")
                        .setParameter("id", id);
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(filme, MediaType.APPLICATION_JSON).build();
    }    
    
}
