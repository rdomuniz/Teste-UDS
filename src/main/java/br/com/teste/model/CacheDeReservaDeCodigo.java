package br.com.teste.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheDeReservaDeCodigo {
	
	private static CacheDeReservaDeCodigo instance;
	
	public static CacheDeReservaDeCodigo getInstance() {
		if (instance == null)
			instance = new CacheDeReservaDeCodigo();
		return instance;
	}

	private Map<Class<?>, Cache<Object, Object>> cache = new HashMap<>();

	public void reserva(Class<?> clazz, Object codigo) {
		Cache<Object, Object> cacheClass = cache.get(clazz);
		if(cacheClass == null){
			cacheClass = CacheBuilder.newBuilder()
		       .maximumSize(1000)
		       .expireAfterWrite(60, TimeUnit.MINUTES)
		       .build();
			cache.put(clazz, cacheClass);
		}
		cacheClass.put(codigo, codigo);
	}
	
	public void libera(Class<?> clazz, Object codigo) {
		Cache<Object, Object> cacheClass = cache.get(clazz);
		cacheClass.invalidate(codigo);
	}

	public List<Object> listar(Class<?> clazz) {
		List<Object> codigos = new ArrayList<Object>();
		if(cache.get(clazz) != null) {
			for (Entry<Object, Object> entry : cache.get(clazz).asMap().entrySet()) {
				codigos.add(entry.getValue());
			}
		}
		return codigos;
	}
	
}
