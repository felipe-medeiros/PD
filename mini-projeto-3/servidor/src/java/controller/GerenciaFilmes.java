/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.Filme;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Felipe
 */

@WebService(serviceName = "gerencia")
public class GerenciaFilmes {
    
    @WebMethod(operationName="add")
    public Filme cadastrar(@WebParam(name="titulo") String titulo, @WebParam(name="diretor") String diretor,
            @WebParam(name="genero") String genero, @WebParam(name="estudio") String estudio,
            @WebParam(name="ano") String ano){
        
        Filme filme = new Filme();
        filme.setAno(ano);
        filme.setDiretor(diretor);
        filme.setEstudio(estudio);
        filme.setGenero(genero);
        filme.setTitulo(titulo);    
        
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        
        session.getTransaction().begin();
        session.save(filme);
        session.getTransaction().commit();
        
        session.close();
        
        return filme;
    }
    
    @WebMethod(operationName="update")
    public String atualizar(@WebParam(name="id") String ids, @WebParam(name="titulo") String titulo){
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        Long id = Long.parseLong(ids);
        
        session.getTransaction().begin();
        Query q = session.createSQLQuery("update filme set titulo = :titulo where filme.id = :id")
                        .setParameter("id", id)
                        .setParameter("titulo", titulo);
        
        q.executeUpdate();
        session.getTransaction().commit();
        
        session.close();
        return "Atualizado com sucesso.";
    }
    
    @WebMethod(operationName="remove")
    public String remover(@WebParam(name="id") String ids){
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        System.out.println(ids);
        int id = Integer.parseInt(ids);
        
        session.getTransaction().begin();
        Query q = session.createSQLQuery("delete from filme where filme.id = :id")
                        .setParameter("id", id);
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        
        return "Removido com sucesso.";
    }
    
    @WebMethod(operationName="get")
    public List<Object[]> consultar(@WebParam(name="texto") String texto, @WebParam(name="criterio") String criterios){
        System.out.println(criterios);
        Query q;
        SessionFactory sessionF = new Configuration().configure().buildSessionFactory();
        Session session = sessionF.openSession();
        session.getTransaction().begin();
        switch (criterios){
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
        
        return rows;
    }
}

