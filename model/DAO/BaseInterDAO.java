package model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;

public interface BaseInterDAO<entity> {
    public Connection getConnection();
    public boolean inserir(entity e);
    public boolean deletar(entity e);
    public boolean alterar(entity e);
    public ResultSet encontrarPorCampoEspecifico(entity e, String field);
    public ResultSet encontrarTodos();
}
