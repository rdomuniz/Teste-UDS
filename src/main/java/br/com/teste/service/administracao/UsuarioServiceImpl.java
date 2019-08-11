package br.com.teste.service.administracao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.teste.service.GeralServiceImpl;

@Service
@Transactional
public class UsuarioServiceImpl extends GeralServiceImpl implements UsuarioService {

}