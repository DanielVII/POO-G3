package model.Service;

import java.util.List;

public interface BaseInterBO<entity> {
	public boolean inserir (entity e);
	public boolean deletar (entity e);
	public boolean alterar (entity e);
	public List<entity> listarPorCampoEspecifico (entity e, String campo);
	public List<entity> listarTodos();
}
